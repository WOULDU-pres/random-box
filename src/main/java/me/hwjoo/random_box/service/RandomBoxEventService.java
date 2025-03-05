package me.hwjoo.random_box.service;

import lombok.RequiredArgsConstructor;
import me.hwjoo.random_box.dto.mapper.RandomBoxEventMapper;
import me.hwjoo.random_box.dto.request.RandomBoxEventRequest;
import me.hwjoo.random_box.dto.request.RandomBoxEventUpdateRequest;
import me.hwjoo.random_box.dto.response.RandomBoxEventDetailResponse;
import me.hwjoo.random_box.dto.response.RandomBoxEventsResponse;
import me.hwjoo.random_box.entity.RandomBoxEvent;
import me.hwjoo.random_box.entity.RandomBoxEventStatus;
import me.hwjoo.random_box.exception.RandomBoxEventNotFoundException;
import me.hwjoo.random_box.repository.RandomBoxEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 랜덤 박스 이벤트 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RandomBoxEventService {

    private final RandomBoxEventRepository randomBoxEventRepository;
    
    /**
     * 모든 랜덤 박스 이벤트를 조회합니다.
     *
     * @return 이벤트 목록 응답 DTO
     */
    public RandomBoxEventsResponse getAllEvents() {
        List<RandomBoxEvent> events = randomBoxEventRepository.findAll();
        return RandomBoxEventMapper.toRandomBoxEventsResponse(events);
    }
    
    /**
     * 특정 상태의 모든 랜덤 박스 이벤트를 조회합니다.
     *
     * @param status 조회할 이벤트 상태
     * @return 이벤트 목록 응답 DTO
     */
    public RandomBoxEventsResponse getEventsByStatus(RandomBoxEventStatus status) {
        List<RandomBoxEvent> events = randomBoxEventRepository.findByStatus(status);
        return RandomBoxEventMapper.toRandomBoxEventsResponse(events);
    }
    
    /**
     * 현재 활성화된 모든 랜덤 박스 이벤트를 조회합니다.
     *
     * @return 활성화된 이벤트 목록 응답 DTO
     */
    public RandomBoxEventsResponse getActiveEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<RandomBoxEvent> events = randomBoxEventRepository.findByStatusAndStartDateBeforeAndEndDateAfter(
                RandomBoxEventStatus.ACTIVE, now, now);
        return RandomBoxEventMapper.toRandomBoxEventsResponse(events);
    }
    
    /**
     * 특정 ID의 랜덤 박스 이벤트 상세 정보를 조회합니다.
     *
     * @param eventId 조회할 이벤트 ID
     * @return 이벤트 상세 정보 응답 DTO
     * @throws RandomBoxEventNotFoundException 이벤트를 찾을 수 없는 경우
     */
    public RandomBoxEventDetailResponse getEventDetail(String eventId) {
        RandomBoxEvent event = randomBoxEventRepository.findById(eventId)
                .orElseThrow(() -> new RandomBoxEventNotFoundException("이벤트를 찾을 수 없습니다. ID: " + eventId));
        return RandomBoxEventMapper.toRandomBoxEventDetailResponse(event);
    }
    
    /**
     * 새로운 랜덤 박스 이벤트를 생성합니다.
     *
     * @param request 생성할 이벤트 요청 DTO
     * @return 생성된 이벤트 상세 정보 응답 DTO
     */
    @Transactional
    public RandomBoxEventDetailResponse createEvent(RandomBoxEventRequest request) {
        RandomBoxEvent event = request.toEntity();
        RandomBoxEvent savedEvent = randomBoxEventRepository.save(event);
        return RandomBoxEventMapper.toRandomBoxEventDetailResponse(savedEvent);
    }
    
    /**
     * 특정 ID의 랜덤 박스 이벤트를 업데이트합니다.
     *
     * @param eventId 업데이트할 이벤트 ID
     * @param request 업데이트할 이벤트 정보 요청 DTO
     * @return 업데이트된 이벤트 상세 정보 응답 DTO
     * @throws RandomBoxEventNotFoundException 이벤트를 찾을 수 없는 경우
     */
    @Transactional
    public RandomBoxEventDetailResponse updateEvent(String eventId, RandomBoxEventUpdateRequest request) {
        RandomBoxEvent event = randomBoxEventRepository.findById(eventId)
                .orElseThrow(() -> new RandomBoxEventNotFoundException("이벤트를 찾을 수 없습니다. ID: " + eventId));
        
        event.update(
            request.getTitle(), 
            request.getDescription(), 
            request.getImageUrl(), 
            request.getStartDate(), 
            request.getEndDate(), 
            request.getStatus()
        );
        
        RandomBoxEvent updatedEvent = randomBoxEventRepository.save(event);
        return RandomBoxEventMapper.toRandomBoxEventDetailResponse(updatedEvent);
    }
    
    /**
     * 특정 ID의 랜덤 박스 이벤트 상태를 업데이트합니다.
     *
     * @param eventId 업데이트할 이벤트 ID
     * @param status 업데이트할 이벤트 상태
     * @return 업데이트된 이벤트 상세 정보 응답 DTO
     * @throws RandomBoxEventNotFoundException 이벤트를 찾을 수 없는 경우
     */
    @Transactional
    public RandomBoxEventDetailResponse updateEventStatus(String eventId, RandomBoxEventStatus status) {
        RandomBoxEvent event = randomBoxEventRepository.findById(eventId)
                .orElseThrow(() -> new RandomBoxEventNotFoundException("이벤트를 찾을 수 없습니다. ID: " + eventId));
        
        event.updateStatus(status);
        RandomBoxEvent updatedEvent = randomBoxEventRepository.save(event);
        return RandomBoxEventMapper.toRandomBoxEventDetailResponse(updatedEvent);
    }
    
    /**
     * 특정 ID의 랜덤 박스 이벤트를 삭제합니다.
     *
     * @param eventId 삭제할 이벤트 ID
     * @throws RandomBoxEventNotFoundException 이벤트를 찾을 수 없는 경우
     */
    @Transactional
    public void deleteEvent(String eventId) {
        if (!randomBoxEventRepository.existsById(eventId)) {
            throw new RandomBoxEventNotFoundException("이벤트를 찾을 수 없습니다. ID: " + eventId);
        }
        randomBoxEventRepository.deleteById(eventId);
    }
} 