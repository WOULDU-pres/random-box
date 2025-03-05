package me.hwjoo.random_box.repository;

import me.hwjoo.random_box.entity.RandomBox;
import me.hwjoo.random_box.entity.RandomBoxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RandomBox 엔티티에 대한 데이터 액세스를 제공하는 리포지토리 인터페이스
 */
@Repository
public interface RandomBoxRepository extends JpaRepository<RandomBox, String> {

    /**
     * 특정 이벤트에 포함된 모든 랜덤 박스를 조회합니다.
     *
     * @param event 조회할 이벤트
     * @return 이벤트에 포함된 랜덤 박스 목록
     */
    List<RandomBox> findByEvent(RandomBoxEvent event);
    
    /**
     * 특정 이벤트에 포함된 모든 랜덤 박스를 이벤트 ID로 조회합니다.
     *
     * @param eventId 조회할 이벤트 ID
     * @return 이벤트에 포함된 랜덤 박스 목록
     */
    List<RandomBox> findByEventId(String eventId);
    
    /**
     * 특정 이벤트에 포함된 모든 랜덤 박스를 이름으로 정렬하여 조회합니다.
     *
     * @param eventId 조회할 이벤트 ID
     * @return 이벤트에 포함된 랜덤 박스 목록 (이름순 정렬)
     */
    List<RandomBox> findByEventIdOrderByNameAsc(String eventId);
    
    /**
     * 특정 이벤트에 포함된 모든 랜덤 박스를 가격으로 정렬하여 조회합니다.
     *
     * @param eventId 조회할 이벤트 ID
     * @return 이벤트에 포함된 랜덤 박스 목록 (가격순 정렬)
     */
    List<RandomBox> findByEventIdOrderByPriceAsc(String eventId);
} 