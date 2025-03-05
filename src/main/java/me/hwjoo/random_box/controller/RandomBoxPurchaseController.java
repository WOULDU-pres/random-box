package me.hwjoo.random_box.controller;

import lombok.RequiredArgsConstructor;
import me.hwjoo.random_box.dto.request.RandomBoxPurchaseRequest;
import me.hwjoo.random_box.dto.response.RandomBoxPurchaseHistoryResponse;
import me.hwjoo.random_box.dto.response.RandomBoxPurchaseResponse;
import me.hwjoo.random_box.service.RandomBoxPurchaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 랜덤 박스 구매 관련 API 엔드포인트를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class RandomBoxPurchaseController {

    private final RandomBoxPurchaseService randomBoxPurchaseService;

    /**
     * 랜덤 박스를 구매합니다.
     *
     * @param request 구매 정보 DTO
     * @return 구매 결과 응답
     */
    @PostMapping
    public ResponseEntity<RandomBoxPurchaseResponse> purchaseRandomBox(@RequestBody RandomBoxPurchaseRequest request) {
        return new ResponseEntity<>(
                randomBoxPurchaseService.purchaseRandomBox(request),
                HttpStatus.CREATED
        );
    }

    /**
     * 구매한 랜덤 박스의 아이템을 공개합니다.
     *
     * @param purchaseId 공개할 구매 ID
     * @return 공개 결과 응답
     */
    @PostMapping("/{purchaseId}/reveal")
    public ResponseEntity<RandomBoxPurchaseResponse> revealRandomBoxItem(@PathVariable("purchaseId") String purchaseId) {
        return ResponseEntity.ok(randomBoxPurchaseService.revealRandomBoxItem(purchaseId));
    }

    /**
     * 특정 구매 내역을 조회합니다.
     *
     * @param purchaseId 조회할 구매 ID
     * @return 구매 내역 응답
     */
    @GetMapping("/{purchaseId}")
    public ResponseEntity<RandomBoxPurchaseResponse> getPurchaseDetail(@PathVariable("purchaseId") String purchaseId) {
        return ResponseEntity.ok(randomBoxPurchaseService.getPurchaseDetail(purchaseId));
    }

    /**
     * 특정 사용자의 구매 내역을 조회합니다.
     *
     * @param userUuid 조회할 사용자 UUID
     * @return 구매 내역 목록 응답
     */
    @GetMapping("/user/{userUuid}")
    public ResponseEntity<RandomBoxPurchaseHistoryResponse> getPurchaseHistory(@PathVariable("userUuid") String userUuid) {
        return ResponseEntity.ok(randomBoxPurchaseService.getPurchaseHistory(userUuid));
    }

    /**
     * 특정 사용자의 구매 내역을 페이징하여 조회합니다.
     *
     * @param userUuid 조회할 사용자 UUID
     * @param pageable 페이징 정보
     * @return 구매 내역 페이지
     */
    @GetMapping("/user/{userUuid}/paged")
    public ResponseEntity<Page<RandomBoxPurchaseResponse>> getPurchaseHistoryPaged(
            @PathVariable("userUuid") String userUuid,
            Pageable pageable) {
        return ResponseEntity.ok(randomBoxPurchaseService.getPurchaseHistoryPaged(userUuid, pageable));
    }
} 