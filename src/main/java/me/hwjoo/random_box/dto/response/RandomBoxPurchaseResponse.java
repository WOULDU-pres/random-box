package me.hwjoo.random_box.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hwjoo.random_box.entity.ItemCategory;
import me.hwjoo.random_box.entity.RandomBoxPurchase;
import me.hwjoo.random_box.entity.RandomBoxPurchaseResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RandomBoxPurchaseResponse {

    private String purchaseId;
    private String userUuid;
    private LocalDateTime purchaseDate;
    private String boxId;
    private String boxName;
    private BigDecimal boxPrice;
    private boolean revealed;
    private RevealedItemDto revealedItem;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RevealedItemDto {
        private String itemId;
        private String itemName;
        private String itemDescription;
        private String itemImageUrl;
        private ItemCategory itemCategory;
        private BigDecimal itemValue;
        private LocalDateTime revealedAt;

        public static RevealedItemDtoBuilder builder() {
            return new RevealedItemDtoBuilder();
        }

        public static class RevealedItemDtoBuilder {
            private String itemId;
            private String itemName;
            private String itemDescription;
            private String itemImageUrl;
            private ItemCategory itemCategory;
            private BigDecimal itemValue;
            private LocalDateTime revealedAt;

            RevealedItemDtoBuilder() {
            }

            public RevealedItemDtoBuilder itemId(String itemId) {
                this.itemId = itemId;
                return this;
            }

            public RevealedItemDtoBuilder itemName(String itemName) {
                this.itemName = itemName;
                return this;
            }

            public RevealedItemDtoBuilder itemDescription(String itemDescription) {
                this.itemDescription = itemDescription;
                return this;
            }

            public RevealedItemDtoBuilder itemImageUrl(String itemImageUrl) {
                this.itemImageUrl = itemImageUrl;
                return this;
            }

            public RevealedItemDtoBuilder itemCategory(ItemCategory itemCategory) {
                this.itemCategory = itemCategory;
                return this;
            }

            public RevealedItemDtoBuilder itemValue(BigDecimal itemValue) {
                this.itemValue = itemValue;
                return this;
            }

            public RevealedItemDtoBuilder revealedAt(LocalDateTime revealedAt) {
                this.revealedAt = revealedAt;
                return this;
            }

            public RevealedItemDto build() {
                return new RevealedItemDto(itemId, itemName, itemDescription, itemImageUrl, 
                        itemCategory, itemValue, revealedAt);
            }

            public String toString() {
                return "RevealedItemDto.RevealedItemDtoBuilder(itemId=" + this.itemId + 
                        ", itemName=" + this.itemName + 
                        ", itemDescription=" + this.itemDescription + 
                        ", itemImageUrl=" + this.itemImageUrl + 
                        ", itemCategory=" + this.itemCategory + 
                        ", itemValue=" + this.itemValue + 
                        ", revealedAt=" + this.revealedAt + ")";
            }
        }
    }

    public static RandomBoxPurchaseResponseBuilder builder() {
        return new RandomBoxPurchaseResponseBuilder();
    }

    public static class RandomBoxPurchaseResponseBuilder {
        private String purchaseId;
        private String userUuid;
        private LocalDateTime purchaseDate;
        private String boxId;
        private String boxName;
        private BigDecimal boxPrice;
        private boolean revealed;
        private RevealedItemDto revealedItem;

        RandomBoxPurchaseResponseBuilder() {
        }

        public RandomBoxPurchaseResponseBuilder purchaseId(String purchaseId) {
            this.purchaseId = purchaseId;
            return this;
        }

        public RandomBoxPurchaseResponseBuilder userUuid(String userUuid) {
            this.userUuid = userUuid;
            return this;
        }

        public RandomBoxPurchaseResponseBuilder purchaseDate(LocalDateTime purchaseDate) {
            this.purchaseDate = purchaseDate;
            return this;
        }

        public RandomBoxPurchaseResponseBuilder boxId(String boxId) {
            this.boxId = boxId;
            return this;
        }

        public RandomBoxPurchaseResponseBuilder boxName(String boxName) {
            this.boxName = boxName;
            return this;
        }

        public RandomBoxPurchaseResponseBuilder boxPrice(BigDecimal boxPrice) {
            this.boxPrice = boxPrice;
            return this;
        }

        public RandomBoxPurchaseResponseBuilder revealed(boolean revealed) {
            this.revealed = revealed;
            return this;
        }

        public RandomBoxPurchaseResponseBuilder revealedItem(RevealedItemDto revealedItem) {
            this.revealedItem = revealedItem;
            return this;
        }

        public RandomBoxPurchaseResponse build() {
            return new RandomBoxPurchaseResponse(purchaseId, userUuid, purchaseDate, boxId, 
                    boxName, boxPrice, revealed, revealedItem);
        }

        public String toString() {
            return "RandomBoxPurchaseResponse.RandomBoxPurchaseResponseBuilder(purchaseId=" + this.purchaseId + 
                    ", userUuid=" + this.userUuid + 
                    ", purchaseDate=" + this.purchaseDate + 
                    ", boxId=" + this.boxId + 
                    ", boxName=" + this.boxName + 
                    ", boxPrice=" + this.boxPrice + 
                    ", revealed=" + this.revealed + 
                    ", revealedItem=" + this.revealedItem + ")";
        }
    }

    public static RandomBoxPurchaseResponse from(RandomBoxPurchase purchase) {
        RandomBoxPurchaseResponseBuilder builder = RandomBoxPurchaseResponse.builder()
                .purchaseId(purchase.getId())
                .userUuid(purchase.getUserUuid())
                .purchaseDate(purchase.getPurchaseDate())
                .boxId(purchase.getRandomBox().getId())
                .boxName(purchase.getRandomBox().getName())
                .boxPrice(purchase.getRandomBox().getPrice())
                .revealed(purchase.getPurchaseResult() != null);

        if (purchase.getPurchaseResult() != null) {
            RandomBoxPurchaseResult result = purchase.getPurchaseResult();
            builder.revealedItem(RevealedItemDto.builder()
                    .itemId(result.getRandomBoxItem().getId())
                    .itemName(result.getRandomBoxItem().getName())
                    .itemDescription(result.getRandomBoxItem().getDescription())
                    .itemImageUrl(result.getRandomBoxItem().getImageUrl())
                    .itemCategory(result.getRandomBoxItem().getCategory())
                    .itemValue(result.getRandomBoxItem().getValue())
                    .revealedAt(result.getRevealedAt())
                    .build());
        }

        return builder.build();
    }
} 