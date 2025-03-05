package me.hwjoo.random_box.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hwjoo.random_box.entity.RandomBoxEvent;
import me.hwjoo.random_box.entity.RandomBoxEventStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 랜덤 박스 이벤트 생성 및 수정 요청 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomBoxEventRequest {

    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private RandomBoxEventStatus status;

    @Builder
    public RandomBoxEventRequest(String id, String title, String description, String imageUrl,
                               LocalDateTime startDate, LocalDateTime endDate, RandomBoxEventStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    /**
     * DTO를 엔티티로 변환합니다.
     * 
     * @return 변환된 RandomBoxEvent 엔티티
     */
    public RandomBoxEvent toEntity() {
        String eventId = (id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id;
        
        return RandomBoxEvent.builder()
                .id(eventId)
                .title(title)
                .description(description)
                .imageUrl(imageUrl)
                .startDate(startDate)
                .endDate(endDate)
                .status(status != null ? status : RandomBoxEventStatus.DRAFT)
                .build();
    }
} 