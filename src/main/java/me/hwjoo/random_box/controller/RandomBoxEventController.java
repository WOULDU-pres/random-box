package me.hwjoo.random_box.controller;

import lombok.RequiredArgsConstructor;
import me.hwjoo.random_box.dto.request.RandomBoxEventRequest;
import me.hwjoo.random_box.dto.request.RandomBoxEventUpdateRequest;
import me.hwjoo.random_box.dto.response.RandomBoxEventDetailResponse;
import me.hwjoo.random_box.dto.response.RandomBoxEventsResponse;
import me.hwjoo.random_box.entity.RandomBoxEventStatus;
import me.hwjoo.random_box.service.RandomBoxEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 랜덤 박스 이벤트 관련 API 엔드포인트를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class RandomBoxEventController {

    private final RandomBoxEventService randomBoxEventService;

    /**
     * 모든 랜덤 박스 이벤트를 조회합니다.
     *
     * @return 이벤트 목록 응답
     */
    @GetMapping
    public ResponseEntity<RandomBoxEventsResponse> getAllEvents() {
        return ResponseEntity.ok(randomBoxEventService.getAllEvents());
    }

    /**
     * 특정 상태의 모든 랜덤 박스 이벤트를 조회합니다.
     *
     * @param status 조회할 이벤트 상태
     * @return 이벤트 목록 응답
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<RandomBoxEventsResponse> getEventsByStatus(@PathVariable("status") RandomBoxEventStatus status) {
        return ResponseEntity.ok(randomBoxEventService.getEventsByStatus(status));
    }

    /**
     * 현재 활성화된 모든 랜덤 박스 이벤트를 조회합니다.
     *
     * @return 활성화된 이벤트 목록 응답
     */
    @GetMapping("/active")
    public ResponseEntity<RandomBoxEventsResponse> getActiveEvents() {
        return ResponseEntity.ok(randomBoxEventService.getActiveEvents());
    }

    /**
     * 특정 ID의 랜덤 박스 이벤트 상세 정보를 조회합니다.
     *
     * @param eventId 조회할 이벤트 ID
     * @return 이벤트 상세 정보 응답
     */
    @GetMapping("/{eventId}")
    public ResponseEntity<RandomBoxEventDetailResponse> getEventDetail(@PathVariable("eventId") String eventId) {
        return ResponseEntity.ok(randomBoxEventService.getEventDetail(eventId));
    }

    /**
     * 새로운 랜덤 박스 이벤트를 생성합니다.
     *
     * @param request 생성할 이벤트 요청 DTO
     * @return 생성된 이벤트 상세 정보 응답
     */
    @PostMapping
    public ResponseEntity<RandomBoxEventDetailResponse> createEvent(@RequestBody RandomBoxEventRequest request) {
        return new ResponseEntity<>(randomBoxEventService.createEvent(request), HttpStatus.CREATED);
    }

    /**
     * 특정 ID의 랜덤 박스 이벤트를 업데이트합니다.
     *
     * @param eventId 업데이트할 이벤트 ID
     * @param request 업데이트할 이벤트 정보 요청 DTO
     * @return 업데이트된 이벤트 상세 정보 응답
     */
    @PutMapping("/{eventId}")
    public ResponseEntity<RandomBoxEventDetailResponse> updateEvent(
            @PathVariable("eventId") String eventId,
            @RequestBody RandomBoxEventUpdateRequest request) {
        return ResponseEntity.ok(randomBoxEventService.updateEvent(eventId, request));
    }

    /**
     * 특정 ID의 랜덤 박스 이벤트 상태를 업데이트합니다.
     *
     * @param eventId 업데이트할 이벤트 ID
     * @param status 업데이트할 이벤트 상태
     * @return 업데이트된 이벤트 상세 정보 응답
     */
    @PatchMapping("/{eventId}/status")
    public ResponseEntity<RandomBoxEventDetailResponse> updateEventStatus(
            @PathVariable("eventId") String eventId,
            @RequestParam("status") RandomBoxEventStatus status) {
        return ResponseEntity.ok(randomBoxEventService.updateEventStatus(eventId, status));
    }

    /**
     * 특정 ID의 랜덤 박스 이벤트를 삭제합니다.
     *
     * @param eventId 삭제할 이벤트 ID
     * @return 응답 엔티티
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("eventId") String eventId) {
        randomBoxEventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
} 