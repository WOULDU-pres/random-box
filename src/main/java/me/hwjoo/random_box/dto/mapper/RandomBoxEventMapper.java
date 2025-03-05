package me.hwjoo.random_box.dto.mapper;

import me.hwjoo.random_box.dto.response.RandomBoxEventDetailResponse;
import me.hwjoo.random_box.dto.response.RandomBoxEventsResponse;
import me.hwjoo.random_box.entity.RandomBoxEvent;

import java.util.List;

/**
 * RandomBoxEvent 엔티티와 관련 DTO 간의 변환을 처리하는 매퍼 클래스
 */
public class RandomBoxEventMapper {

    /**
     * RandomBoxEvent 엔티티 목록을 RandomBoxEventsResponse DTO로 변환합니다.
     *
     * @param events RandomBoxEvent 엔티티 목록
     * @return RandomBoxEventsResponse DTO
     */
    public static RandomBoxEventsResponse toRandomBoxEventsResponse(List<RandomBoxEvent> events) {
        return RandomBoxEventsResponse.from(events);
    }

    /**
     * RandomBoxEvent 엔티티를 RandomBoxEventDetailResponse DTO로 변환합니다.
     *
     * @param event RandomBoxEvent 엔티티
     * @return RandomBoxEventDetailResponse DTO
     */
    public static RandomBoxEventDetailResponse toRandomBoxEventDetailResponse(RandomBoxEvent event) {
        return RandomBoxEventDetailResponse.from(event);
    }
} 