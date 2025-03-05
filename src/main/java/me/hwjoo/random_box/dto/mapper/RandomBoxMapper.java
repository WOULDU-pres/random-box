package me.hwjoo.random_box.dto.mapper;

import me.hwjoo.random_box.dto.response.RandomBoxDetailResponse;
import me.hwjoo.random_box.entity.RandomBox;

/**
 * RandomBox 엔티티와 관련 DTO 간의 변환을 처리하는 매퍼 클래스
 */
public class RandomBoxMapper {

    /**
     * RandomBox 엔티티를 RandomBoxDetailResponse DTO로 변환합니다.
     *
     * @param randomBox RandomBox 엔티티
     * @return RandomBoxDetailResponse DTO
     */
    public static RandomBoxDetailResponse toRandomBoxDetailResponse(RandomBox randomBox) {
        return RandomBoxDetailResponse.from(randomBox);
    }
} 