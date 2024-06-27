package lecture.infrastructure.lecture.applyhistory;

import lecture.domain.lecture.applyhistory.ApplyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplyHistoryJpaRepository extends JpaRepository<ApplyHistory,Long> {
    Optional<ApplyHistory> findByUserIdAndLectureId(Long userId, Long lectureId);
}
