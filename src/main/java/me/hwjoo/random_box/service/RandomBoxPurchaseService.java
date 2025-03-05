package me.hwjoo.random_box.service;

import lombok.RequiredArgsConstructor;
import me.hwjoo.random_box.dto.mapper.RandomBoxPurchaseMapper;
import me.hwjoo.random_box.dto.request.RandomBoxPurchaseRequest;
import me.hwjoo.random_box.dto.response.RandomBoxPurchaseHistoryResponse;
import me.hwjoo.random_box.dto.response.RandomBoxPurchaseResponse;
import me.hwjoo.random_box.entity.RandomBox;
import me.hwjoo.random_box.entity.RandomBoxItem;
import me.hwjoo.random_box.entity.RandomBoxPurchase;
import me.hwjoo.random_box.entity.RandomBoxPurchaseResult;
import me.hwjoo.random_box.exception.RandomBoxNotFoundException;
import me.hwjoo.random_box.exception.RandomBoxPurchaseNotFoundException;
import me.hwjoo.random_box.exception.RandomBoxSoldOutException;
import me.hwjoo.random_box.repository.RandomBoxPurchaseRepository;
import me.hwjoo.random_box.repository.RandomBoxPurchaseResultRepository;
import me.hwjoo.random_box.repository.RandomBoxRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 랜덤 박스 구매 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class RandomBoxPurchaseService {

    private final RandomBoxRepository randomBoxRepository;
    private final RandomBoxPurchaseRepository randomBoxPurchaseRepository;
    private final RandomBoxPurchaseResultRepository randomBoxPurchaseResultRepository;
    private final RandomBoxDrawService randomBoxDrawService;
    private final RandomBoxService randomBoxService;
    
    /**
     * 랜덤 박스를 구매합니다.
     *
     * @param boxId 구매할 랜덤 박스 ID
     * @param userUuid 구매자 UUID
     * @return 구매 결과 응답 DTO
     * @throws RandomBoxNotFoundException 랜덤 박스를 찾을 수 없는 경우
     * @throws RandomBoxSoldOutException 랜덤 박스가 품절된 경우
     */
    @Transactional
    public RandomBoxPurchaseResponse purchaseRandomBox(String boxId, String userUuid) {
        // 요청 DTO 객체 생성
        RandomBoxPurchaseRequest request = RandomBoxPurchaseRequest.builder()
                .boxId(boxId)
                .userUuid(userUuid)
                .build();
                
        return purchaseRandomBox(request);
    }
    
    /**
     * 랜덤 박스를 구매합니다.
     *
     * @param request 구매 요청 DTO
     * @return 구매 결과 응답 DTO
     * @throws RandomBoxNotFoundException 랜덤 박스를 찾을 수 없는 경우
     * @throws RandomBoxSoldOutException 랜덤 박스가 품절된 경우
     */
    @Transactional
    public RandomBoxPurchaseResponse purchaseRandomBox(RandomBoxPurchaseRequest request) {
        RandomBox randomBox = randomBoxRepository.findById(request.getBoxId())
                .orElseThrow(() -> new RandomBoxNotFoundException("랜덤 박스를 찾을 수 없습니다. ID: " + request.getBoxId()));
        
        if (randomBox.isSoldOut()) {
            throw new RandomBoxSoldOutException("랜덤 박스가 품절되었습니다. ID: " + request.getBoxId());
        }
        
        // 판매량 증가 (RandomBoxService 활용)
        randomBoxService.incrementSoldCount(request.getBoxId());
        
        // 구매 기록 생성 (RandomBoxPurchase 엔티티의 Builder에 맞게 수정)
        RandomBoxPurchase purchase = RandomBoxPurchase.builder()
                .id(UUID.randomUUID().toString())
                .userUuid(request.getUserUuid())
                .randomBox(randomBox)
                .build();
        
        RandomBoxPurchase savedPurchase = randomBoxPurchaseRepository.save(purchase);
        
        return RandomBoxPurchaseMapper.toRandomBoxPurchaseResponse(savedPurchase);
    }
    
    /**
     * 구매한 랜덤 박스의 아이템을 공개합니다.
     *
     * @param purchaseId 공개할 구매 ID
     * @return 공개 결과 응답 DTO
     * @throws RandomBoxPurchaseNotFoundException 구매 내역을 찾을 수 없는 경우
     */
    @Transactional
    public RandomBoxPurchaseResponse revealRandomBoxItem(String purchaseId) {
        RandomBoxPurchase purchase = randomBoxPurchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RandomBoxPurchaseNotFoundException("구매 내역을 찾을 수 없습니다. ID: " + purchaseId));
        
        // 이미 공개된 경우 기존 결과 반환
        if (purchase.getPurchaseResult() != null) {
            return RandomBoxPurchaseMapper.toRandomBoxPurchaseResponse(purchase);
        }
        
        // 아이템 추첨
        RandomBoxItem drawnItem = randomBoxDrawService.drawRandomBoxItem(purchase.getRandomBox().getId());
        
        // 구매 결과 생성 (RandomBoxPurchaseResult 엔티티의 Builder에 맞게 수정)
        RandomBoxPurchaseResult result = RandomBoxPurchaseResult.builder()
                .id(UUID.randomUUID().toString())
                .randomBoxPurchase(purchase)
                .randomBoxItem(drawnItem)
                .build();
        
        RandomBoxPurchaseResult savedResult = randomBoxPurchaseResultRepository.save(result);
        
        // 구매 결과 연결 (여기는 setters가 없어 직접 수정 불가, 새 객체 생성 필요)
        // purchase.setPurchaseResult(savedResult);
        // RandomBoxPurchase updatedPurchase = randomBoxPurchaseRepository.save(purchase);
        
        // 결과를 포함한 구매 정보 다시 조회
        RandomBoxPurchase updatedPurchase = randomBoxPurchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RandomBoxPurchaseNotFoundException("구매 내역을 찾을 수 없는 경우"));
        
        return RandomBoxPurchaseMapper.toRandomBoxPurchaseResponse(updatedPurchase);
    }
    
    /**
     * 특정 구매 내역을 조회합니다.
     *
     * @param purchaseId 조회할 구매 ID
     * @return 구매 내역 응답 DTO
     * @throws RandomBoxPurchaseNotFoundException 구매 내역을 찾을 수 없는 경우
     */
    @Transactional(readOnly = true)
    public RandomBoxPurchaseResponse getPurchaseDetail(String purchaseId) {
        RandomBoxPurchase purchase = randomBoxPurchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RandomBoxPurchaseNotFoundException("구매 내역을 찾을 수 없습니다. ID: " + purchaseId));
        
        return RandomBoxPurchaseMapper.toRandomBoxPurchaseResponse(purchase);
    }
    
    /**
     * 특정 사용자의 구매 내역을 조회합니다.
     *
     * @param userUuid 조회할 사용자 UUID
     * @return 구매 내역 목록 응답 DTO
     */
    @Transactional(readOnly = true)
    public RandomBoxPurchaseHistoryResponse getPurchaseHistory(String userUuid) {
        List<RandomBoxPurchase> purchases = randomBoxPurchaseRepository.findByUserUuid(userUuid);
        return RandomBoxPurchaseMapper.toRandomBoxPurchaseHistoryResponse(userUuid, purchases);
    }
    
    /**
     * 특정 사용자의 구매 내역을 페이징하여 조회합니다.
     *
     * @param userUuid 조회할 사용자 UUID
     * @param pageable 페이징 정보
     * @return 구매 내역 페이지 응답 DTO
     */
    @Transactional(readOnly = true)
    public Page<RandomBoxPurchaseResponse> getPurchaseHistoryPaged(String userUuid, Pageable pageable) {
        Page<RandomBoxPurchase> purchasePage = randomBoxPurchaseRepository.findByUserUuid(userUuid, pageable);
        return purchasePage.map(RandomBoxPurchaseMapper::toRandomBoxPurchaseResponse);
    }
} 