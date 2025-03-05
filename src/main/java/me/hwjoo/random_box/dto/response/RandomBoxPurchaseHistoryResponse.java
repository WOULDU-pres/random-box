package me.hwjoo.random_box.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hwjoo.random_box.entity.ItemCategory;
import me.hwjoo.random_box.entity.RandomBoxPurchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RandomBoxPurchaseHistoryResponse {

    private String userUuid;
    private List<PurchaseHistoryDto> purchases;
    private int totalCount;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseHistoryDto {
        private String purchaseId;
        private LocalDateTime purchaseDate;
        private String boxId;
        private String boxName;
        private String boxImageUrl;
        private BigDecimal boxPrice;
        private boolean revealed;
        private RevealedItemSummaryDto revealedItem;

        public static PurchaseHistoryDtoBuilder builder() {
            return new PurchaseHistoryDtoBuilder();
        }

        public static class PurchaseHistoryDtoBuilder {
            private String purchaseId;
            private LocalDateTime purchaseDate;
            private String boxId;
            private String boxName;
            private String boxImageUrl;
            private BigDecimal boxPrice;
            private boolean revealed;
            private RevealedItemSummaryDto revealedItem;

            PurchaseHistoryDtoBuilder() {
            }

            public PurchaseHistoryDtoBuilder purchaseId(String purchaseId) {
                this.purchaseId = purchaseId;
                return this;
            }

            public PurchaseHistoryDtoBuilder purchaseDate(LocalDateTime purchaseDate) {
                this.purchaseDate = purchaseDate;
                return this;
            }

            public PurchaseHistoryDtoBuilder boxId(String boxId) {
                this.boxId = boxId;
                return this;
            }

            public PurchaseHistoryDtoBuilder boxName(String boxName) {
                this.boxName = boxName;
                return this;
            }

            public PurchaseHistoryDtoBuilder boxImageUrl(String boxImageUrl) {
                this.boxImageUrl = boxImageUrl;
                return this;
            }

            public PurchaseHistoryDtoBuilder boxPrice(BigDecimal boxPrice) {
                this.boxPrice = boxPrice;
                return this;
            }

            public PurchaseHistoryDtoBuilder revealed(boolean revealed) {
                this.revealed = revealed;
                return this;
            }

            public PurchaseHistoryDtoBuilder revealedItem(RevealedItemSummaryDto revealedItem) {
                this.revealedItem = revealedItem;
                return this;
            }

            public PurchaseHistoryDto build() {
                return new PurchaseHistoryDto(purchaseId, purchaseDate, boxId, boxName, 
                        boxImageUrl, boxPrice, revealed, revealedItem);
            }

            public String toString() {
                return "PurchaseHistoryDto.PurchaseHistoryDtoBuilder(purchaseId=" + this.purchaseId + 
                        ", purchaseDate=" + this.purchaseDate + 
                        ", boxId=" + this.boxId + 
                        ", boxName=" + this.boxName + 
                        ", boxImageUrl=" + this.boxImageUrl + 
                        ", boxPrice=" + this.boxPrice + 
                        ", revealed=" + this.revealed + 
                        ", revealedItem=" + this.revealedItem + ")";
            }
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RevealedItemSummaryDto {
        private String itemId;
        private String itemName;
        private String itemImageUrl;
        private ItemCategory itemCategory;
        private BigDecimal itemValue;
        private LocalDateTime revealedAt;

        public static RevealedItemSummaryDtoBuilder builder() {
            return new RevealedItemSummaryDtoBuilder();
        }

        public static class RevealedItemSummaryDtoBuilder {
            private String itemId;
            private String itemName;
            private String itemImageUrl;
            private ItemCategory itemCategory;
            private BigDecimal itemValue;
            private LocalDateTime revealedAt;

            RevealedItemSummaryDtoBuilder() {
            }

            public RevealedItemSummaryDtoBuilder itemId(String itemId) {
                this.itemId = itemId;
                return this;
            }

            public RevealedItemSummaryDtoBuilder itemName(String itemName) {
                this.itemName = itemName;
                return this;
            }

            public RevealedItemSummaryDtoBuilder itemImageUrl(String itemImageUrl) {
                this.itemImageUrl = itemImageUrl;
                return this;
            }

            public RevealedItemSummaryDtoBuilder itemCategory(ItemCategory itemCategory) {
                this.itemCategory = itemCategory;
                return this;
            }

            public RevealedItemSummaryDtoBuilder itemValue(BigDecimal itemValue) {
                this.itemValue = itemValue;
                return this;
            }

            public RevealedItemSummaryDtoBuilder revealedAt(LocalDateTime revealedAt) {
                this.revealedAt = revealedAt;
                return this;
            }

            public RevealedItemSummaryDto build() {
                return new RevealedItemSummaryDto(itemId, itemName, itemImageUrl, itemCategory, 
                        itemValue, revealedAt);
            }

            public String toString() {
                return "RevealedItemSummaryDto.RevealedItemSummaryDtoBuilder(itemId=" + this.itemId + 
                        ", itemName=" + this.itemName + 
                        ", itemImageUrl=" + this.itemImageUrl + 
                        ", itemCategory=" + this.itemCategory + 
                        ", itemValue=" + this.itemValue + 
                        ", revealedAt=" + this.revealedAt + ")";
            }
        }
    }

    public static RandomBoxPurchaseHistoryResponseBuilder builder() {
        return new RandomBoxPurchaseHistoryResponseBuilder();
    }

    public static class RandomBoxPurchaseHistoryResponseBuilder {
        private String userUuid;
        private List<PurchaseHistoryDto> purchases;
        private int totalCount;

        RandomBoxPurchaseHistoryResponseBuilder() {
        }

        public RandomBoxPurchaseHistoryResponseBuilder userUuid(String userUuid) {
            this.userUuid = userUuid;
            return this;
        }

        public RandomBoxPurchaseHistoryResponseBuilder purchases(List<PurchaseHistoryDto> purchases) {
            this.purchases = purchases;
            return this;
        }

        public RandomBoxPurchaseHistoryResponseBuilder totalCount(int totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public RandomBoxPurchaseHistoryResponse build() {
            return new RandomBoxPurchaseHistoryResponse(userUuid, purchases, totalCount);
        }

        public String toString() {
            return "RandomBoxPurchaseHistoryResponse.RandomBoxPurchaseHistoryResponseBuilder(userUuid=" + this.userUuid + 
                    ", purchases=" + this.purchases + 
                    ", totalCount=" + this.totalCount + ")";
        }
    }

    public static RandomBoxPurchaseHistoryResponse from(String userUuid, List<RandomBoxPurchase> purchases) {
        List<PurchaseHistoryDto> purchaseDtos = purchases.stream()
                .map(purchase -> {
                    PurchaseHistoryDto.PurchaseHistoryDtoBuilder builder = PurchaseHistoryDto.builder()
                            .purchaseId(purchase.getId())
                            .purchaseDate(purchase.getPurchaseDate())
                            .boxId(purchase.getRandomBox().getId())
                            .boxName(purchase.getRandomBox().getName())
                            .boxImageUrl(purchase.getRandomBox().getImageUrl())
                            .boxPrice(purchase.getRandomBox().getPrice())
                            .revealed(purchase.getPurchaseResult() != null);

                    if (purchase.getPurchaseResult() != null) {
                        builder.revealedItem(RevealedItemSummaryDto.builder()
                                .itemId(purchase.getPurchaseResult().getRandomBoxItem().getId())
                                .itemName(purchase.getPurchaseResult().getRandomBoxItem().getName())
                                .itemImageUrl(purchase.getPurchaseResult().getRandomBoxItem().getImageUrl())
                                .itemCategory(purchase.getPurchaseResult().getRandomBoxItem().getCategory())
                                .itemValue(purchase.getPurchaseResult().getRandomBoxItem().getValue())
                                .revealedAt(purchase.getPurchaseResult().getRevealedAt())
                                .build());
                    }

                    return builder.build();
                })
                .collect(Collectors.toList());

        return RandomBoxPurchaseHistoryResponse.builder()
                .userUuid(userUuid)
                .purchases(purchaseDtos)
                .totalCount(purchaseDtos.size())
                .build();
    }
} 