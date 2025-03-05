package me.hwjoo.random_box.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hwjoo.random_box.entity.RandomBoxEventStatus;
import me.hwjoo.random_box.entity.RandomBoxEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RandomBoxEventsResponse {

    private List<RandomBoxEventDto> events;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RandomBoxEventDto {
        private String id;
        private String title;
        private String description;
        private String imageUrl;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private RandomBoxEventStatus status;
        private int boxCount;

        public static RandomBoxEventDtoBuilder builder() {
            return new RandomBoxEventDtoBuilder();
        }

        public static class RandomBoxEventDtoBuilder {
            private String id;
            private String title;
            private String description;
            private String imageUrl;
            private LocalDateTime startDate;
            private LocalDateTime endDate;
            private RandomBoxEventStatus status;
            private int boxCount;

            RandomBoxEventDtoBuilder() {
            }

            public RandomBoxEventDtoBuilder id(String id) {
                this.id = id;
                return this;
            }

            public RandomBoxEventDtoBuilder title(String title) {
                this.title = title;
                return this;
            }

            public RandomBoxEventDtoBuilder description(String description) {
                this.description = description;
                return this;
            }

            public RandomBoxEventDtoBuilder imageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
                return this;
            }

            public RandomBoxEventDtoBuilder startDate(LocalDateTime startDate) {
                this.startDate = startDate;
                return this;
            }

            public RandomBoxEventDtoBuilder endDate(LocalDateTime endDate) {
                this.endDate = endDate;
                return this;
            }

            public RandomBoxEventDtoBuilder status(RandomBoxEventStatus status) {
                this.status = status;
                return this;
            }

            public RandomBoxEventDtoBuilder boxCount(int boxCount) {
                this.boxCount = boxCount;
                return this;
            }

            public RandomBoxEventDto build() {
                return new RandomBoxEventDto(id, title, description, imageUrl, startDate, endDate, status, boxCount);
            }

            public String toString() {
                return "RandomBoxEventDto.RandomBoxEventDtoBuilder(id=" + this.id + 
                        ", title=" + this.title + 
                        ", description=" + this.description + 
                        ", imageUrl=" + this.imageUrl + 
                        ", startDate=" + this.startDate + 
                        ", endDate=" + this.endDate + 
                        ", status=" + this.status + 
                        ", boxCount=" + this.boxCount + ")";
            }
        }
    }

    public static RandomBoxEventsResponseBuilder builder() {
        return new RandomBoxEventsResponseBuilder();
    }

    public static class RandomBoxEventsResponseBuilder {
        private List<RandomBoxEventDto> events;

        RandomBoxEventsResponseBuilder() {
        }

        public RandomBoxEventsResponseBuilder events(List<RandomBoxEventDto> events) {
            this.events = events;
            return this;
        }

        public RandomBoxEventsResponse build() {
            return new RandomBoxEventsResponse(events);
        }

        public String toString() {
            return "RandomBoxEventsResponse.RandomBoxEventsResponseBuilder(events=" + this.events + ")";
        }
    }

    public static RandomBoxEventsResponse from(List<RandomBoxEvent> events) {
        List<RandomBoxEventDto> eventDtos = events.stream()
                .map(event -> RandomBoxEventDto.builder()
                        .id(event.getId())
                        .title(event.getTitle())
                        .description(event.getDescription())
                        .imageUrl(event.getImageUrl())
                        .startDate(event.getStartDate())
                        .endDate(event.getEndDate())
                        .status(event.getStatus())
                        .boxCount(event.getBoxes().size())
                        .build())
                .collect(Collectors.toList());

        return RandomBoxEventsResponse.builder()
                .events(eventDtos)
                .build();
    }
} 