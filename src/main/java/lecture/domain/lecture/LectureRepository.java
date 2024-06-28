package lecture.domain.lecture;

import java.util.List;
import java.util.Optional;

public interface LectureRepository {

    Lecture save(Lecture lecture);
    Optional<Lecture> findById(long lectureId);
    Optional<Lecture> findByWithPessimisticLock(Long id);
    List<Lecture> findAll();
    void deleteAllInBatch();
}
