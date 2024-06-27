package lecture.domain.lecture;

import java.util.Optional;

public interface LectureRepository {

    Lecture save(Lecture lecture);
    Optional<Lecture> findById(long lectureId);
    Optional<Lecture> findByWithPessimisticLock(Long id);
    void deleteAllInBatch();
}
