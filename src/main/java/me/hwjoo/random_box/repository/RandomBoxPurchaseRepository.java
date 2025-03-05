package me.hwjoo.random_box.repository;

import me.hwjoo.random_box.entity.RandomBox;
import me.hwjoo.random_box.entity.RandomBoxPurchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * RandomBoxPurchase 엔티티에 대한 데이터 액세스를 제공하는 리포지토리 인터페이스
 */
@Repository
public interface RandomBoxPurchaseRepository extends JpaRepository<RandomBoxPurchase, String> {

    /**
     * 특정 사용자의 모든 구매 내역을 조회합니다.
     *
     * @param userUuid 조회할 사용자 UUID
     * @return 사용자의 구매 내역 목록
     */
    List<RandomBoxPurchase> findByUserUuid(String userUuid);
    
    /**
     * 특정 사용자의 모든 구매 내역을 페이징하여 조회합니다.
     *
     * @param userUuid 조회할 사용자 UUID
     * @param pageable 페이징 정보
     * @return 사용자의 구매 내역 페이지
     */
    Page<RandomBoxPurchase> findByUserUuid(String userUuid, Pageable pageable);
    
    /**
     * 특정 사용자의 특정 랜덤 박스 구매 내역을 조회합니다.
     *
     * @param userUuid 조회할 사용자 UUID
     * @param randomBox 조회할 랜덤 박스
     * @return 사용자의 특정 랜덤 박스 구매 내역 목록
     */
    List<RandomBoxPurchase> findByUserUuidAndRandomBox(String userUuid, RandomBox randomBox);
    
    /**
     * 특정 사용자의 특정 랜덤 박스 구매 내역을 랜덤 박스 ID로 조회합니다.
     *
     * @param userUuid 조회할 사용자 UUID
     * @param randomBoxId 조회할 랜덤 박스 ID
     * @return 사용자의 특정 랜덤 박스 구매 내역 목록
     */
    List<RandomBoxPurchase> findByUserUuidAndRandomBoxId(String userUuid, String randomBoxId);
    
    /**
     * 특정 기간 내의 모든 구매 내역을 조회합니다.
     *
     * @param startDate 조회 시작 일시
     * @param endDate 조회 종료 일시
     * @return 특정 기간 내의 구매 내역 목록
     */
    List<RandomBoxPurchase> findByPurchaseDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * 특정 사용자의 특정 기간 내 구매 내역을 조회합니다.
     *
     * @param userUuid 조회할 사용자 UUID
     * @param startDate 조회 시작 일시
     * @param endDate 조회 종료 일시
     * @return 사용자의 특정 기간 내 구매 내역 목록
     */
    List<RandomBoxPurchase> findByUserUuidAndPurchaseDateBetween(
            String userUuid, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * 특정 랜덤 박스의 총 판매량을 계산합니다.
     *
     * @param randomBoxId 조회할 랜덤 박스 ID
     * @return 총 판매량
     */
    @Query("SELECT COUNT(p) FROM RandomBoxPurchase p WHERE p.randomBox.id = :randomBoxId")
    Long countByRandomBoxId(String randomBoxId);
} 