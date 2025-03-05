package me.hwjoo.random_box.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hwjoo.random_box.entity.BoxType;
import me.hwjoo.random_box.entity.ItemCategory;
import me.hwjoo.random_box.entity.RandomBox;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RandomBoxDetailResponse {

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private BoxType type;
    private int soldCount;
    private int maxQuantity;
    private boolean soldOut;
    private String eventId;
    private String eventTitle;
    private List<RandomBoxItemDto> items;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RandomBoxItemDto {
        private String id;
        private String name;
        private String description;
        private String imageUrl;
        private ItemCategory category;
        private BigDecimal value;
        private Double probability;

        public static RandomBoxItemDtoBuilder builder() {
            return new RandomBoxItemDtoBuilder();
        }

        public static class RandomBoxItemDtoBuilder {
            private String id;
            private String name;
            private String description;
            private String imageUrl;
            private ItemCategory category;
            private BigDecimal value;
            private Double probability;

            RandomBoxItemDtoBuilder() {
            }

            public RandomBoxItemDtoBuilder id(String id) {
                this.id = id;
                return this;
            }

            public RandomBoxItemDtoBuilder name(String name) {
                this.name = name;
                return this;
            }

            public RandomBoxItemDtoBuilder description(String description) {
                this.description = description;
                return this;
            }

            public RandomBoxItemDtoBuilder imageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
                return this;
            }

            public RandomBoxItemDtoBuilder category(ItemCategory category) {
                this.category = category;
                return this;
            }

            public RandomBoxItemDtoBuilder value(BigDecimal value) {
                this.value = value;
                return this;
            }

            public RandomBoxItemDtoBuilder probability(Double probability) {
                this.probability = probability;
                return this;
            }

            public RandomBoxItemDto build() {
                return new RandomBoxItemDto(id, name, description, imageUrl, category, value, probability);
            }

            public String toString() {
                return "RandomBoxItemDto.RandomBoxItemDtoBuilder(id=" + this.id + 
                        ", name=" + this.name + 
                        ", description=" + this.description + 
                        ", imageUrl=" + this.imageUrl + 
                        ", category=" + this.category + 
                        ", value=" + this.value + 
                        ", probability=" + this.probability + ")";
            }
        }
    }

    public static RandomBoxDetailResponseBuilder builder() {
        return new RandomBoxDetailResponseBuilder();
    }

    public static class RandomBoxDetailResponseBuilder {
        private String id;
        private String name;
        private String description;
        private String imageUrl;
        private BigDecimal price;
        private BoxType type;
        private int soldCount;
        private int maxQuantity;
        private boolean soldOut;
        private String eventId;
        private String eventTitle;
        private List<RandomBoxItemDto> items;

        RandomBoxDetailResponseBuilder() {
        }

        public RandomBoxDetailResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public RandomBoxDetailResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RandomBoxDetailResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public RandomBoxDetailResponseBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public RandomBoxDetailResponseBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public RandomBoxDetailResponseBuilder type(BoxType type) {
            this.type = type;
            return this;
        }

        public RandomBoxDetailResponseBuilder soldCount(int soldCount) {
            this.soldCount = soldCount;
            return this;
        }

        public RandomBoxDetailResponseBuilder maxQuantity(int maxQuantity) {
            this.maxQuantity = maxQuantity;
            return this;
        }

        public RandomBoxDetailResponseBuilder soldOut(boolean soldOut) {
            this.soldOut = soldOut;
            return this;
        }

        public RandomBoxDetailResponseBuilder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public RandomBoxDetailResponseBuilder eventTitle(String eventTitle) {
            this.eventTitle = eventTitle;
            return this;
        }

        public RandomBoxDetailResponseBuilder items(List<RandomBoxItemDto> items) {
            this.items = items;
            return this;
        }

        public RandomBoxDetailResponse build() {
            return new RandomBoxDetailResponse(id, name, description, imageUrl, price, type, soldCount, 
                    maxQuantity, soldOut, eventId, eventTitle, items);
        }

        public String toString() {
            return "RandomBoxDetailResponse.RandomBoxDetailResponseBuilder(id=" + this.id + 
                    ", name=" + this.name + 
                    ", description=" + this.description + 
                    ", imageUrl=" + this.imageUrl + 
                    ", price=" + this.price + 
                    ", type=" + this.type + 
                    ", soldCount=" + this.soldCount + 
                    ", maxQuantity=" + this.maxQuantity + 
                    ", soldOut=" + this.soldOut + 
                    ", eventId=" + this.eventId + 
                    ", eventTitle=" + this.eventTitle + 
                    ", items=" + this.items + ")";
        }
    }

    public static RandomBoxDetailResponse from(RandomBox randomBox) {
        List<RandomBoxItemDto> itemDtos = randomBox.getItems().stream()
                .map(item -> RandomBoxItemDto.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .imageUrl(item.getImageUrl())
                        .category(item.getCategory())
                        .value(item.getValue())
                        .probability(item.getProbability())
                        .build())
                .collect(Collectors.toList());

        return RandomBoxDetailResponse.builder()
                .id(randomBox.getId())
                .name(randomBox.getName())
                .description(randomBox.getDescription())
                .imageUrl(randomBox.getImageUrl())
                .price(randomBox.getPrice())
                .type(randomBox.getType())
                .soldCount(randomBox.getSoldCount())
                .maxQuantity(randomBox.getMaxQuantity())
                .soldOut(randomBox.isSoldOut())
                .eventId(randomBox.getEvent().getId())
                .eventTitle(randomBox.getEvent().getTitle())
                .items(itemDtos)
                .build();
    }
} 