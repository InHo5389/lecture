package lecture.infrastructure.applyhistory;

import lecture.domain.applyhistory.ApplyHistory;
import lecture.domain.applyhistory.ApplyHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ApplyHistoryRepositoryImpl implements ApplyHistoryRepository {

    private final ApplyHistoryJpaRepository applyHistoryJpaRepository;


    @Override
    public ApplyHistory save(ApplyHistory applyHistory) {
        return applyHistoryJpaRepository.save(ApplyHistoryEntity.from(applyHistory)).toDomain();
    }

    @Override
    public Optional<ApplyHistory> findByUserIdAndLectureId(Long userId, Long lectureId) {
        return applyHistoryJpaRepository.findByUserIdAndLectureId(userId, lectureId).map(ApplyHistoryEntity::toDomain);
    }

    @Override
    public void deleteAllInBatch() {
        applyHistoryJpaRepository.deleteAllInBatch();
    }

}
