package lecture.infrastructure.lecture;

import lecture.domain.lecture.Lecture;
import lecture.domain.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Lecture save(Lecture lecture) {
        return lectureJpaRepository.save(lecture);
    }

    @Override
    public Optional<Lecture> findById(long lectureId) {
        return lectureJpaRepository.findById(lectureId);
    }

    @Override
    public Optional<Lecture> findByWithPessimisticLock(Long lectureId) {
        return lectureJpaRepository.findByWithPessimisticLock(lectureId);
    }

    @Override
    public void deleteAllInBatch() {
        lectureJpaRepository.deleteAllInBatch();
    }
}
