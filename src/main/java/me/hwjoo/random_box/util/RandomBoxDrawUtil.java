package me.hwjoo.random_box.util;

import me.hwjoo.random_box.entity.RandomBox;
import me.hwjoo.random_box.entity.RandomBoxItem;

import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * 랜덤박스에서 아이템을 추첨하는 기능을 제공하는 유틸리티 클래스
 */
public class RandomBoxDrawUtil {

    private static final Random RANDOM = new Random();

    /**
     * 랜덤박스에서 아이템을 추첨합니다.
     * 각 아이템의 확률에 따라 가중치가 부여됩니다.
     *
     * @param randomBox 랜덤박스 엔티티
     * @return 추첨된 아이템
     */
    public static RandomBoxItem drawItem(RandomBox randomBox) {
        List<RandomBoxItem> items = randomBox.getItems();
        
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("랜덤박스에 아이템이 없습니다.");
        }

        // 확률의 합이 100%인지 검증
        validateTotalProbability(items);

        // 가중치 맵 생성
        NavigableMap<Double, RandomBoxItem> weightMap = createWeightMap(items);

        // 랜덤 값 생성 (0.0 ~ 100.0)
        double randomValue = RANDOM.nextDouble() * 100.0;

        // 랜덤 값에 해당하는 아이템 반환
        return weightMap.ceilingEntry(randomValue).getValue();
    }

    /**
     * 모든 아이템의 확률 합이 100%인지 검증합니다.
     *
     * @param items 랜덤박스 아이템 목록
     */
    private static void validateTotalProbability(List<RandomBoxItem> items) {
        double totalProbability = items.stream()
                .mapToDouble(RandomBoxItem::getProbability)
                .sum();

        if (Math.abs(totalProbability - 100.0) > 0.001) {
            throw new IllegalArgumentException("아이템 확률의 합이 100%가 아닙니다. 현재 합: " + totalProbability + "%");
        }
    }

    /**
     * 아이템의 확률에 따른 가중치 맵을 생성합니다.
     *
     * @param items 랜덤박스 아이템 목록
     * @return 가중치 맵
     */
    private static NavigableMap<Double, RandomBoxItem> createWeightMap(List<RandomBoxItem> items) {
        NavigableMap<Double, RandomBoxItem> weightMap = new TreeMap<>();
        double cumulative = 0.0;

        for (RandomBoxItem item : items) {
            cumulative += item.getProbability();
            weightMap.put(cumulative, item);
        }

        return weightMap;
    }
} 