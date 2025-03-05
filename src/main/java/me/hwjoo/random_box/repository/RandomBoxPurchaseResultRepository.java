package me.hwjoo.random_box.repository;

import me.hwjoo.random_box.entity.ItemCategory;
import me.hwjoo.random_box.entity.RandomBoxItem;
import me.hwjoo.random_box.entity.RandomBoxPurchase;
import me.hwjoo.random_box.entity.RandomBoxPurchaseResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * RandomBoxPurchaseResult 엔티티에 대한 데이터 액세스를 제공하는 리포지토리 인터페이스
 */
@Repository
public interface RandomBoxPurchaseResultRepository extends JpaRepository<RandomBoxPurchaseResult, String> {

    /**
     * 특정 구매 내역의 결과를 조회합니다.
     *
     * @param randomBoxPurchase 조회할 구매 내역
     * @return 구매 결과 (Optional)
     */
    Optional<RandomBoxPurchaseResult> findByRandomBoxPurchase(RandomBoxPurchase randomBoxPurchase);
    
    /**
     * 특정 구매 내역의 결과를 구매 ID로 조회합니다.
     *
     * @param randomBoxPurchaseId 조회할 구매 ID
     * @return 구매 결과 (Optional)
     */
    @Query("SELECT r FROM RandomBoxPurchaseResult r WHERE r.randomBoxPurchase.id = :randomBoxPurchaseId")
    Optional<RandomBoxPurchaseResult> findByRandomBoxPurchaseId(String randomBoxPurchaseId);
    
    /**
     * 특정 아이템이 포함된 모든 구매 결과를 조회합니다.
     *
     * @param randomBoxItem 조회할 아이템
     * @return 해당 아이템이 포함된 구매 결과 목록
     */
    List<RandomBoxPurchaseResult> findByRandomBoxItem(RandomBoxItem randomBoxItem);
    
    /**
     * 특정 아이템이 포함된 모든 구매 결과를 아이템 ID로 조회합니다.
     *
     * @param randomBoxItemId 조회할 아이템 ID
     * @return 해당 아이템이 포함된 구매 결과 목록
     */
    List<RandomBoxPurchaseResult> findByRandomBoxItemId(String randomBoxItemId);
    
    /**
     * 특정 기간 내의 모든 구매 결과를 조회합니다.
     *
     * @param startDate 조회 시작 일시
     * @param endDate 조회 종료 일시
     * @return 특정 기간 내의 구매 결과 목록
     */
    List<RandomBoxPurchaseResult> findByRevealedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * 특정 카테고리의 아이템이 포함된 모든 구매 결과를 조회합니다.
     *
     * @param category 조회할 아이템 카테고리
     * @return 해당 카테고리의 아이템이 포함된 구매 결과 목록
     */
    @Query("SELECT r FROM RandomBoxPurchaseResult r WHERE r.randomBoxItem.category = :category")
    List<RandomBoxPurchaseResult> findByItemCategory(ItemCategory category);
    
    /**
     * 특정 랜덤 박스의 아이템별 당첨 횟수를 계산합니다.
     *
     * @param randomBoxId 조회할 랜덤 박스 ID
     * @return 아이템 ID와 당첨 횟수를 포함하는 객체 배열 목록
     */
    @Query("SELECT r.randomBoxItem.id, COUNT(r) FROM RandomBoxPurchaseResult r " +
           "JOIN r.randomBoxPurchase p WHERE p.randomBox.id = :randomBoxId " +
           "GROUP BY r.randomBoxItem.id")
    List<Object[]> countItemWinsByRandomBoxId(String randomBoxId);
} 