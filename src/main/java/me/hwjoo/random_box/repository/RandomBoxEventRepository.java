package me.hwjoo.random_box.repository;

import me.hwjoo.random_box.entity.RandomBoxEvent;
import me.hwjoo.random_box.entity.RandomBoxEventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * RandomBoxEvent 엔티티에 대한 데이터 액세스를 제공하는 리포지토리 인터페이스
 */
@Repository
public interface RandomBoxEventRepository extends JpaRepository<RandomBoxEvent, String> {

    /**
     * 특정 상태의 모든 이벤트를 조회합니다.
     *
     * @param status 조회할 이벤트 상태
     * @return 해당 상태의 이벤트 목록
     */
    List<RandomBoxEvent> findByStatus(RandomBoxEventStatus status);
    
    /**
     * 현재 활성화된 모든 이벤트를 조회합니다.
     *
     * @param now 현재 시간
     * @return 활성화된 이벤트 목록
     */
    List<RandomBoxEvent> findByStatusAndStartDateBeforeAndEndDateAfter(
            RandomBoxEventStatus status, LocalDateTime now, LocalDateTime now2);
    
    /**
     * 특정 날짜에 종료되는 모든 이벤트를 조회합니다.
     *
     * @param startOfDay 날짜의 시작(00:00:00)
     * @param endOfDay 날짜의 끝(23:59:59)
     * @return 해당 날짜에 종료되는 이벤트 목록
     */
    List<RandomBoxEvent> findByEndDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
} 