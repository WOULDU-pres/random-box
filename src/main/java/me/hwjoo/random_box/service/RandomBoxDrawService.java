package me.hwjoo.random_box.service;

import lombok.RequiredArgsConstructor;
import me.hwjoo.random_box.entity.RandomBox;
import me.hwjoo.random_box.entity.RandomBoxItem;
import me.hwjoo.random_box.exception.RandomBoxNotFoundException;
import me.hwjoo.random_box.repository.RandomBoxItemRepository;
import me.hwjoo.random_box.repository.RandomBoxRepository;
import me.hwjoo.random_box.util.RandomBoxDrawUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 랜덤 박스 아이템 추첨 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RandomBoxDrawService {

    private final RandomBoxRepository randomBoxRepository;
    private final RandomBoxItemRepository randomBoxItemRepository;
    
    /**
     * 특정 랜덤 박스에서 아이템을 추첨합니다.
     *
     * @param boxId 추첨할 랜덤 박스 ID
     * @return 추첨된 아이템
     * @throws RandomBoxNotFoundException 랜덤 박스를 찾을 수 없는 경우
     * @throws IllegalArgumentException 랜덤 박스에 아이템이 없거나 확률 합계가 100%가 아닌 경우
     */
    public RandomBoxItem drawRandomBoxItem(String boxId) {
        RandomBox randomBox = randomBoxRepository.findById(boxId)
                .orElseThrow(() -> new RandomBoxNotFoundException("랜덤 박스를 찾을 수 없습니다. ID: " + boxId));
        
        return RandomBoxDrawUtil.drawItem(randomBox);
    }
    
    /**
     * 특정 랜덤 박스에 포함된 아이템들의 확률 합계를 검증합니다.
     *
     * @param boxId 검증할 랜덤 박스 ID
     * @return 확률 합계가 100%인지 여부
     * @throws RandomBoxNotFoundException 랜덤 박스를 찾을 수 없는 경우
     */
    public boolean validateItemProbabilities(String boxId) {
        if (!randomBoxRepository.existsById(boxId)) {
            throw new RandomBoxNotFoundException("랜덤 박스를 찾을 수 없습니다. ID: " + boxId);
        }
        
        Double totalProbability = randomBoxItemRepository.sumProbabilityByRandomBoxId(boxId);
        
        if (totalProbability == null) {
            return false;
        }
        
        return Math.abs(totalProbability - 100.0) < 0.001;
    }
    
    /**
     * 특정 랜덤 박스에 포함된 아이템들의 확률을 조정하여 합계가 100%가 되도록 합니다.
     *
     * @param boxId 조정할 랜덤 박스 ID
     * @return 조정된 아이템 목록
     * @throws RandomBoxNotFoundException 랜덤 박스를 찾을 수 없는 경우
     */
    @Transactional
    public List<RandomBoxItem> normalizeItemProbabilities(String boxId) {
        if (!randomBoxRepository.existsById(boxId)) {
            throw new RandomBoxNotFoundException("랜덤 박스를 찾을 수 없습니다. ID: " + boxId);
        }
        
        List<RandomBoxItem> items = randomBoxItemRepository.findByRandomBoxId(boxId);
        
        if (items.isEmpty()) {
            return items;
        }
        
        double totalProbability = items.stream()
                .mapToDouble(RandomBoxItem::getProbability)
                .sum();
        
        // 확률 합계가 이미 100%인 경우 조정하지 않음
        if (Math.abs(totalProbability - 100.0) < 0.001) {
            return items;
        }
        
        // 각 아이템의 확률을 비율에 맞게 조정
        double normalizationFactor = 100.0 / totalProbability;
        
        for (RandomBoxItem item : items) {
            double normalizedProbability = item.getProbability() * normalizationFactor;
            item.setProbability(normalizedProbability);
        }
        
        return randomBoxItemRepository.saveAll(items);
    }
} 