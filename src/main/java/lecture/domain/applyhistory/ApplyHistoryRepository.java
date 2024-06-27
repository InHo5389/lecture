package lecture.domain.applyhistory;

import java.util.Optional;

public interface ApplyHistoryRepository {

    ApplyHistory save(ApplyHistory applyHistory);
    Optional<ApplyHistory> findByUserIdAndLectureId(Long userId, Long lectureId);
    void deleteAllInBatch();

}
