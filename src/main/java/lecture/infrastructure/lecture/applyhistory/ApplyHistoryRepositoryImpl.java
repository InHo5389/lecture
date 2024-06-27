package lecture.infrastructure.lecture.applyhistory;

import lecture.domain.lecture.applyhistory.ApplyHistory;
import lecture.domain.lecture.applyhistory.ApplyHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ApplyHistoryRepositoryImpl implements ApplyHistoryRepository {

    private final ApplyHistoryJpaRepository applyHistoryJpaRepository;


    @Override
    public ApplyHistory save(ApplyHistory applyHistory) {
        return applyHistoryJpaRepository.save(applyHistory);
    }

    @Override
    public Optional<ApplyHistory> findByUserIdAndLectureId(Long userId, Long lectureId) {
        return applyHistoryJpaRepository.findByUserIdAndLectureId(userId, lectureId);
    }

    @Override
    public void deleteAllInBatch() {
        applyHistoryJpaRepository.deleteAllInBatch();
    }

}
