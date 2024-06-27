package lecture.infrastructure.lecture;

import lecture.domain.lecture.Lecture;
import lecture.domain.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Lecture save(Lecture lecture) {
        return lectureJpaRepository.save(LectureEntity.from(lecture)).toDomain();
    }

    @Override
    public Optional<Lecture> findById(long lectureId) {
        return lectureJpaRepository.findById(lectureId).map(LectureEntity::toDomain);
    }

    @Override
    public Optional<Lecture> findByWithPessimisticLock(Long lectureId) {
        return lectureJpaRepository.findByWithPessimisticLock(lectureId).map(LectureEntity::toDomain);
    }

    @Override
    public List<Lecture> findAll() {
        return lectureJpaRepository.findAll().stream().map(LectureEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteAllInBatch() {
        lectureJpaRepository.deleteAllInBatch();
    }
}
