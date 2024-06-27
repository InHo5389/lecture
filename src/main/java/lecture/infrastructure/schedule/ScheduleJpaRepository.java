package lecture.infrastructure.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity,Long> {
    List<ScheduleEntity> findByLectureId(Long lectureId);
}
