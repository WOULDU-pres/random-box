package me.hwjoo.random_box.service;

import lombok.RequiredArgsConstructor;
import me.hwjoo.random_box.dto.mapper.RandomBoxMapper;
import me.hwjoo.random_box.dto.request.RandomBoxRequest;
import me.hwjoo.random_box.dto.response.RandomBoxDetailResponse;
import me.hwjoo.random_box.entity.RandomBox;
import me.hwjoo.random_box.entity.RandomBoxEvent;
import me.hwjoo.random_box.exception.RandomBoxEventNotFoundException;
import me.hwjoo.random_box.exception.RandomBoxNotFoundException;
import me.hwjoo.random_box.exception.RandomBoxSoldOutException;
import me.hwjoo.random_box.repository.RandomBoxEventRepository;
import me.hwjoo.random_box.repository.RandomBoxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 랜덤 박스 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RandomBoxService {

    private final RandomBoxRepository randomBoxRepository;
    private final RandomBoxEventRepository randomBoxEventRepository;
    
    /**
     * 특정 ID의 랜덤 박스 상세 정보를 조회합니다.
     *
     * @param boxId 조회할 랜덤 박스 ID
     * @return 랜덤 박스 상세 정보 응답 DTO
     * @throws RandomBoxNotFoundException 랜덤 박스를 찾을 수 없는 경우
     */
    public RandomBoxDetailResponse getBoxDetail(String boxId) {
        RandomBox randomBox = randomBoxRepository.findById(boxId)
                .orElseThrow(() -> new RandomBoxNotFoundException("랜덤 박스를 찾을 수 없습니다. ID: " + boxId));
        return RandomBoxMapper.toRandomBoxDetailResponse(randomBox);
    }
    
    /**
     * 특정 이벤트에 포함된 모든 랜덤 박스를 조회합니다.
     *
     * @param eventId 조회할 이벤트 ID
     * @return 랜덤 박스 상세 정보 응답 DTO 목록
     * @throws RandomBoxEventNotFoundException 이벤트를 찾을 수 없는 경우
     */
    public List<RandomBoxDetailResponse> getBoxesByEventId(String eventId) {
        if (!randomBoxEventRepository.existsById(eventId)) {
            throw new RandomBoxEventNotFoundException("이벤트를 찾을 수 없습니다. ID: " + eventId);
        }
        List<RandomBox> randomBoxes = randomBoxRepository.findByEventId(eventId);
        return randomBoxes.stream()
                .map(RandomBoxMapper::toRandomBoxDetailResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * 특정 이벤트에 포함된 모든 랜덤 박스를 이름으로 정렬하여 조회합니다.
     *
     * @param eventId 조회할 이벤트 ID
     * @return 랜덤 박스 상세 정보 응답 DTO 목록 (이름순 정렬)
     * @throws RandomBoxEventNotFoundException 이벤트를 찾을 수 없는 경우
     */
    public List<RandomBoxDetailResponse> getBoxesByEventIdSortByName(String eventId) {
        if (!randomBoxEventRepository.existsById(eventId)) {
            throw new RandomBoxEventNotFoundException("이벤트를 찾을 수 없습니다. ID: " + eventId);
        }
        List<RandomBox> randomBoxes = randomBoxRepository.findByEventIdOrderByNameAsc(eventId);
        return randomBoxes.stream()
                .map(RandomBoxMapper::toRandomBoxDetailResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * 특정 이벤트에 포함된 모든 랜덤 박스를 가격으로 정렬하여 조회합니다.
     *
     * @param eventId 조회할 이벤트 ID
     * @return 랜덤 박스 상세 정보 응답 DTO 목록 (가격순 정렬)
     * @throws RandomBoxEventNotFoundException 이벤트를 찾을 수 없는 경우
     */
    public List<RandomBoxDetailResponse> getBoxesByEventIdSortByPrice(String eventId) {
        if (!randomBoxEventRepository.existsById(eventId)) {
            throw new RandomBoxEventNotFoundException("이벤트를 찾을 수 없습니다. ID: " + eventId);
        }
        List<RandomBox> randomBoxes = randomBoxRepository.findByEventIdOrderByPriceAsc(eventId);
        return randomBoxes.stream()
                .map(RandomBoxMapper::toRandomBoxDetailResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * 새로운 랜덤 박스를 생성합니다.
     *
     * @param request 생성할 랜덤 박스 요청 DTO
     * @return 생성된 랜덤 박스 상세 정보 응답 DTO
     * @throws RandomBoxEventNotFoundException 이벤트를 찾을 수 없는 경우
     */
    @Transactional
    public RandomBoxDetailResponse createBox(RandomBoxRequest request) {
        RandomBoxEvent event = randomBoxEventRepository.findById(request.getEventId())
                .orElseThrow(() -> new RandomBoxEventNotFoundException("이벤트를 찾을 수 없습니다. ID: " + request.getEventId()));
        
        RandomBox randomBox = request.toEntity(event);
        RandomBox savedBox = randomBoxRepository.save(randomBox);
        
        return RandomBoxMapper.toRandomBoxDetailResponse(savedBox);
    }
    
    /**
     * 특정 ID의 랜덤 박스를 업데이트합니다.
     *
     * @param boxId 업데이트할 랜덤 박스 ID
     * @param request 업데이트할 랜덤 박스 정보 요청 DTO
     * @return 업데이트된 랜덤 박스 상세 정보 응답 DTO
     * @throws RandomBoxNotFoundException 랜덤 박스를 찾을 수 없는 경우
     * @throws RandomBoxEventNotFoundException 이벤트를 찾을 수 없는 경우
     */
    @Transactional
    public RandomBoxDetailResponse updateBox(String boxId, RandomBoxRequest request) {
        RandomBox randomBox = randomBoxRepository.findById(boxId)
                .orElseThrow(() -> new RandomBoxNotFoundException("랜덤 박스를 찾을 수 없습니다. ID: " + boxId));
        
        RandomBoxEvent event = null;
        if (request.getEventId() != null && !request.getEventId().equals(randomBox.getEvent().getId())) {
            event = randomBoxEventRepository.findById(request.getEventId())
                    .orElseThrow(() -> new RandomBoxEventNotFoundException("이벤트를 찾을 수 없습니다. ID: " + request.getEventId()));
        }
        
        randomBox.update(
            request.getName(),
            request.getDescription(),
            request.getImageUrl(),
            request.getPrice(),
            request.getType(),
            request.getMaxQuantity()
        );
        
        if (event != null) {
            // 이벤트 변경이 필요한 경우, RandomBox 클래스에 setEvent 메서드가 필요합니다.
            // 현재 코드에서는 업데이트 시 이벤트를 변경하지 않도록 구현
        }
        
        RandomBox updatedBox = randomBoxRepository.save(randomBox);
        return RandomBoxMapper.toRandomBoxDetailResponse(updatedBox);
    }
    
    /**
     * 특정 ID의 랜덤 박스를 삭제합니다.
     *
     * @param boxId 삭제할 랜덤 박스 ID
     * @throws RandomBoxNotFoundException 랜덤 박스를 찾을 수 없는 경우
     */
    @Transactional
    public void deleteBox(String boxId) {
        if (!randomBoxRepository.existsById(boxId)) {
            throw new RandomBoxNotFoundException("랜덤 박스를 찾을 수 없습니다. ID: " + boxId);
        }
        randomBoxRepository.deleteById(boxId);
    }
    
    /**
     * 랜덤 박스 구매 시 재고를 확인하고 판매량을 증가시킵니다.
     *
     * @param boxId 구매할 랜덤 박스 ID
     * @return 업데이트된 랜덤 박스 상세 정보 응답 DTO
     * @throws RandomBoxNotFoundException 랜덤 박스를 찾을 수 없는 경우
     * @throws RandomBoxSoldOutException 랜덤 박스가 품절된 경우
     */
    @Transactional
    public RandomBoxDetailResponse incrementSoldCount(String boxId) {
        RandomBox randomBox = randomBoxRepository.findById(boxId)
                .orElseThrow(() -> new RandomBoxNotFoundException("랜덤 박스를 찾을 수 없습니다. ID: " + boxId));
        
        if (randomBox.isSoldOut()) {
            throw new RandomBoxSoldOutException("랜덤 박스가 품절되었습니다. ID: " + boxId);
        }
        
        randomBox.incrementSoldCount();
        
        RandomBox updatedBox = randomBoxRepository.save(randomBox);
        return RandomBoxMapper.toRandomBoxDetailResponse(updatedBox);
    }
} 