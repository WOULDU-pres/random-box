package me.hwjoo.random_box.controller;

import lombok.RequiredArgsConstructor;
import me.hwjoo.random_box.dto.request.RandomBoxRequest;
import me.hwjoo.random_box.dto.response.RandomBoxDetailResponse;
import me.hwjoo.random_box.service.RandomBoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 랜덤 박스 관련 API 엔드포인트를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api/boxes")
@RequiredArgsConstructor
public class RandomBoxController {

    private final RandomBoxService randomBoxService;

    /**
     * 특정 ID의 랜덤 박스 상세 정보를 조회합니다.
     *
     * @param boxId 조회할 랜덤 박스 ID
     * @return 랜덤 박스 상세 정보 응답
     */
    @GetMapping("/{boxId}")
    public ResponseEntity<RandomBoxDetailResponse> getBoxDetail(@PathVariable("boxId") String boxId) {
        return ResponseEntity.ok(randomBoxService.getBoxDetail(boxId));
    }

    /**
     * 특정 이벤트에 포함된 모든 랜덤 박스를 조회합니다.
     *
     * @param eventId 조회할 이벤트 ID
     * @return 랜덤 박스 상세 정보 목록
     */
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<RandomBoxDetailResponse>> getBoxesByEventId(@PathVariable("eventId") String eventId) {
        return ResponseEntity.ok(randomBoxService.getBoxesByEventId(eventId));
    }

    /**
     * 특정 이벤트에 포함된 모든 랜덤 박스를 이름으로 정렬하여 조회합니다.
     *
     * @param eventId 조회할 이벤트 ID
     * @return 랜덤 박스 상세 정보 목록 (이름순 정렬)
     */
    @GetMapping("/event/{eventId}/sort/name")
    public ResponseEntity<List<RandomBoxDetailResponse>> getBoxesByEventIdSortByName(@PathVariable("eventId") String eventId) {
        return ResponseEntity.ok(randomBoxService.getBoxesByEventIdSortByName(eventId));
    }

    /**
     * 특정 이벤트에 포함된 모든 랜덤 박스를 가격으로 정렬하여 조회합니다.
     *
     * @param eventId 조회할 이벤트 ID
     * @return 랜덤 박스 상세 정보 목록 (가격순 정렬)
     */
    @GetMapping("/event/{eventId}/sort/price")
    public ResponseEntity<List<RandomBoxDetailResponse>> getBoxesByEventIdSortByPrice(@PathVariable("eventId") String eventId) {
        return ResponseEntity.ok(randomBoxService.getBoxesByEventIdSortByPrice(eventId));
    }

    /**
     * 새로운 랜덤 박스를 생성합니다.
     *
     * @param request 생성할 랜덤 박스 요청 DTO
     * @return 생성된 랜덤 박스 상세 정보
     */
    @PostMapping
    public ResponseEntity<RandomBoxDetailResponse> createBox(@RequestBody RandomBoxRequest request) {
        return new ResponseEntity<>(randomBoxService.createBox(request), HttpStatus.CREATED);
    }

    /**
     * 특정 ID의 랜덤 박스를 업데이트합니다.
     *
     * @param boxId 업데이트할 랜덤 박스 ID
     * @param request 업데이트할 랜덤 박스 정보 요청 DTO
     * @return 업데이트된 랜덤 박스 상세 정보
     */
    @PutMapping("/{boxId}")
    public ResponseEntity<RandomBoxDetailResponse> updateBox(
            @PathVariable("boxId") String boxId,
            @RequestBody RandomBoxRequest request) {
        return ResponseEntity.ok(randomBoxService.updateBox(boxId, request));
    }

    /**
     * 특정 ID의 랜덤 박스를 삭제합니다.
     *
     * @param boxId 삭제할 랜덤 박스 ID
     * @return 응답 엔티티
     */
    @DeleteMapping("/{boxId}")
    public ResponseEntity<Void> deleteBox(@PathVariable("boxId") String boxId) {
        randomBoxService.deleteBox(boxId);
        return ResponseEntity.noContent().build();
    }
} 