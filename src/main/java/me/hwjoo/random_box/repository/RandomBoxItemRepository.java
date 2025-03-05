package me.hwjoo.random_box.repository;

import me.hwjoo.random_box.entity.ItemCategory;
import me.hwjoo.random_box.entity.RandomBox;
import me.hwjoo.random_box.entity.RandomBoxItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * RandomBoxItem 엔티티에 대한 데이터 액세스를 제공하는 리포지토리 인터페이스
 */
@Repository
public interface RandomBoxItemRepository extends JpaRepository<RandomBoxItem, String> {

    /**
     * 특정 랜덤 박스에 포함된 모든 아이템을 조회합니다.
     *
     * @param randomBox 조회할 랜덤 박스
     * @return 랜덤 박스에 포함된 아이템 목록
     */
    List<RandomBoxItem> findByRandomBox(RandomBox randomBox);
    
    /**
     * 특정 랜덤 박스에 포함된 모든 아이템을 랜덤 박스 ID로 조회합니다.
     *
     * @param randomBoxId 조회할 랜덤 박스 ID
     * @return 랜덤 박스에 포함된 아이템 목록
     */
    List<RandomBoxItem> findByRandomBoxId(String randomBoxId);
    
    /**
     * 특정 카테고리의 모든 아이템을 조회합니다.
     *
     * @param category 조회할 아이템 카테고리
     * @return 해당 카테고리의 아이템 목록
     */
    List<RandomBoxItem> findByCategory(ItemCategory category);
    
    /**
     * 특정 랜덤 박스에 포함된 특정 카테고리의 모든 아이템을 조회합니다.
     *
     * @param randomBoxId 조회할 랜덤 박스 ID
     * @param category 조회할 아이템 카테고리
     * @return 랜덤 박스에 포함된 해당 카테고리의 아이템 목록
     */
    List<RandomBoxItem> findByRandomBoxIdAndCategory(String randomBoxId, ItemCategory category);
    
    /**
     * 특정 랜덤 박스에 포함된 모든 아이템의 확률 합계를 계산합니다.
     *
     * @param randomBoxId 조회할 랜덤 박스 ID
     * @return 확률 합계
     */
    @Query("SELECT SUM(i.probability) FROM RandomBoxItem i WHERE i.randomBox.id = :randomBoxId")
    Double sumProbabilityByRandomBoxId(String randomBoxId);
    
    /**
     * 특정 가치 이상의 모든 아이템을 조회합니다.
     *
     * @param value 최소 가치
     * @return 해당 가치 이상의 아이템 목록
     */
    List<RandomBoxItem> findByValueGreaterThanEqual(BigDecimal value);
} 