package lecture.infrastructure.applyhistory;

import lecture.domain.applyhistory.ApplyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplyHistoryJpaRepository extends JpaRepository<ApplyHistoryEntity,Long> {
    Optional<ApplyHistoryEntity> findByUserIdAndLectureId(Long userId, Long lectureId);
    Optional<ApplyHistory> findByUserId(Long userId);
}
