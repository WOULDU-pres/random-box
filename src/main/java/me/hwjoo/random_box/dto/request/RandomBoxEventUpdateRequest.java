package me.hwjoo.random_box.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hwjoo.random_box.entity.RandomBoxEventStatus;

import java.time.LocalDateTime;

/**
 * 랜덤 박스 이벤트 수정 요청 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomBoxEventUpdateRequest {

    private String title;
    private String description;
    private String imageUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private RandomBoxEventStatus status;

    @Builder
    public RandomBoxEventUpdateRequest(String title, String description, String imageUrl,
                                     LocalDateTime startDate, LocalDateTime endDate, RandomBoxEventStatus status) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
} 