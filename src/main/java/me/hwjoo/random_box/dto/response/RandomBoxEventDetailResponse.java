package me.hwjoo.random_box.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hwjoo.random_box.entity.BoxType;
import me.hwjoo.random_box.entity.RandomBoxEvent;
import me.hwjoo.random_box.entity.RandomBoxEventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RandomBoxEventDetailResponse {

    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private RandomBoxEventStatus status;
    private List<RandomBoxDto> boxes;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RandomBoxDto {
        private String id;
        private String name;
        private String description;
        private String imageUrl;
        private BigDecimal price;
        private BoxType type;
        private int soldCount;
        private int maxQuantity;
        private boolean soldOut;

        public static RandomBoxDtoBuilder builder() {
            return new RandomBoxDtoBuilder();
        }

        public static class RandomBoxDtoBuilder {
            private String id;
            private String name;
            private String description;
            private String imageUrl;
            private BigDecimal price;
            private BoxType type;
            private int soldCount;
            private int maxQuantity;
            private boolean soldOut;

            RandomBoxDtoBuilder() {
            }

            public RandomBoxDtoBuilder id(String id) {
                this.id = id;
                return this;
            }

            public RandomBoxDtoBuilder name(String name) {
                this.name = name;
                return this;
            }

            public RandomBoxDtoBuilder description(String description) {
                this.description = description;
                return this;
            }

            public RandomBoxDtoBuilder imageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
                return this;
            }

            public RandomBoxDtoBuilder price(BigDecimal price) {
                this.price = price;
                return this;
            }

            public RandomBoxDtoBuilder type(BoxType type) {
                this.type = type;
                return this;
            }

            public RandomBoxDtoBuilder soldCount(int soldCount) {
                this.soldCount = soldCount;
                return this;
            }

            public RandomBoxDtoBuilder maxQuantity(int maxQuantity) {
                this.maxQuantity = maxQuantity;
                return this;
            }

            public RandomBoxDtoBuilder soldOut(boolean soldOut) {
                this.soldOut = soldOut;
                return this;
            }

            public RandomBoxDto build() {
                return new RandomBoxDto(id, name, description, imageUrl, price, type, soldCount, maxQuantity, soldOut);
            }

            public String toString() {
                return "RandomBoxDto.RandomBoxDtoBuilder(id=" + this.id + 
                        ", name=" + this.name + 
                        ", description=" + this.description + 
                        ", imageUrl=" + this.imageUrl + 
                        ", price=" + this.price + 
                        ", type=" + this.type + 
                        ", soldCount=" + this.soldCount + 
                        ", maxQuantity=" + this.maxQuantity + 
                        ", soldOut=" + this.soldOut + ")";
            }
        }
    }

    public static RandomBoxEventDetailResponseBuilder builder() {
        return new RandomBoxEventDetailResponseBuilder();
    }

    public static class RandomBoxEventDetailResponseBuilder {
        private String id;
        private String title;
        private String description;
        private String imageUrl;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private RandomBoxEventStatus status;
        private List<RandomBoxDto> boxes;

        RandomBoxEventDetailResponseBuilder() {
        }

        public RandomBoxEventDetailResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public RandomBoxEventDetailResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public RandomBoxEventDetailResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public RandomBoxEventDetailResponseBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public RandomBoxEventDetailResponseBuilder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public RandomBoxEventDetailResponseBuilder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public RandomBoxEventDetailResponseBuilder status(RandomBoxEventStatus status) {
            this.status = status;
            return this;
        }

        public RandomBoxEventDetailResponseBuilder boxes(List<RandomBoxDto> boxes) {
            this.boxes = boxes;
            return this;
        }

        public RandomBoxEventDetailResponse build() {
            return new RandomBoxEventDetailResponse(id, title, description, imageUrl, startDate, endDate, status, boxes);
        }

        public String toString() {
            return "RandomBoxEventDetailResponse.RandomBoxEventDetailResponseBuilder(id=" + this.id + 
                    ", title=" + this.title + 
                    ", description=" + this.description + 
                    ", imageUrl=" + this.imageUrl + 
                    ", startDate=" + this.startDate + 
                    ", endDate=" + this.endDate + 
                    ", status=" + this.status + 
                    ", boxes=" + this.boxes + ")";
        }
    }

    public static RandomBoxEventDetailResponse from(RandomBoxEvent event) {
        List<RandomBoxDto> boxDtos = event.getBoxes().stream()
                .map(box -> RandomBoxDto.builder()
                        .id(box.getId())
                        .name(box.getName())
                        .description(box.getDescription())
                        .imageUrl(box.getImageUrl())
                        .price(box.getPrice())
                        .type(box.getType())
                        .soldCount(box.getSoldCount())
                        .maxQuantity(box.getMaxQuantity())
                        .soldOut(box.isSoldOut())
                        .build())
                .collect(Collectors.toList());

        return RandomBoxEventDetailResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .imageUrl(event.getImageUrl())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .status(event.getStatus())
                .boxes(boxDtos)
                .build();
    }
} 