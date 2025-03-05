package me.hwjoo.random_box.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hwjoo.random_box.entity.BoxType;
import me.hwjoo.random_box.entity.RandomBox;
import me.hwjoo.random_box.entity.RandomBoxEvent;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 랜덤 박스 생성 및 수정 요청 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomBoxRequest {

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private BoxType type;
    private Integer maxQuantity;
    private String eventId;

    @Builder
    public RandomBoxRequest(String id, String name, String description, String imageUrl,
                          BigDecimal price, BoxType type, Integer maxQuantity, String eventId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.type = type;
        this.maxQuantity = maxQuantity;
        this.eventId = eventId;
    }

    /**
     * DTO를 엔티티로 변환합니다.
     * 
     * @param event 연관된 이벤트 엔티티
     * @return 변환된 RandomBox 엔티티
     */
    public RandomBox toEntity(RandomBoxEvent event) {
        String boxId = (id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id;
        
        return RandomBox.builder()
                .id(boxId)
                .name(name)
                .description(description)
                .imageUrl(imageUrl)
                .price(price)
                .type(type)
                .maxQuantity(maxQuantity)
                .event(event)
                .build();
    }
} 