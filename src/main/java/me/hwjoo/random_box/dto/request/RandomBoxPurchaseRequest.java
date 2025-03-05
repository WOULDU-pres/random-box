package me.hwjoo.random_box.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RandomBoxPurchaseRequest {

    @NotBlank(message = "유저 UUID는 필수입니다.")
    private String userUuid;

    @NotBlank(message = "랜덤 박스 ID는 필수입니다.")
    private String boxId;

    public static RandomBoxPurchaseRequestBuilder builder() {
        return new RandomBoxPurchaseRequestBuilder();
    }

    public static class RandomBoxPurchaseRequestBuilder {
        private String userUuid;
        private String boxId;

        RandomBoxPurchaseRequestBuilder() {
        }

        public RandomBoxPurchaseRequestBuilder userUuid(String userUuid) {
            this.userUuid = userUuid;
            return this;
        }

        public RandomBoxPurchaseRequestBuilder boxId(String boxId) {
            this.boxId = boxId;
            return this;
        }

        public RandomBoxPurchaseRequest build() {
            return new RandomBoxPurchaseRequest(userUuid, boxId);
        }

        public String toString() {
            return "RandomBoxPurchaseRequest.RandomBoxPurchaseRequestBuilder(userUuid=" + this.userUuid + ", boxId=" + this.boxId + ")";
        }
    }
} 