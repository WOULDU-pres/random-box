package me.hwjoo.random_box.dto.mapper;

import me.hwjoo.random_box.dto.response.RandomBoxPurchaseHistoryResponse;
import me.hwjoo.random_box.dto.response.RandomBoxPurchaseResponse;
import me.hwjoo.random_box.entity.RandomBoxPurchase;

import java.util.List;

/**
 * RandomBoxPurchase 엔티티와 관련 DTO 간의 변환을 처리하는 매퍼 클래스
 */
public class RandomBoxPurchaseMapper {

    /**
     * RandomBoxPurchase 엔티티를 RandomBoxPurchaseResponse DTO로 변환합니다.
     *
     * @param purchase RandomBoxPurchase 엔티티
     * @return RandomBoxPurchaseResponse DTO
     */
    public static RandomBoxPurchaseResponse toRandomBoxPurchaseResponse(RandomBoxPurchase purchase) {
        return RandomBoxPurchaseResponse.from(purchase);
    }

    /**
     * RandomBoxPurchase 엔티티 목록을 RandomBoxPurchaseHistoryResponse DTO로 변환합니다.
     *
     * @param userUuid 사용자 UUID
     * @param purchases RandomBoxPurchase 엔티티 목록
     * @return RandomBoxPurchaseHistoryResponse DTO
     */
    public static RandomBoxPurchaseHistoryResponse toRandomBoxPurchaseHistoryResponse(String userUuid, List<RandomBoxPurchase> purchases) {
        return RandomBoxPurchaseHistoryResponse.from(userUuid, purchases);
    }
} 