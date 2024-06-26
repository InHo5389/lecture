package lecture.infrastructure.lecture;

import lecture.domain.lecture.Lecture;
import lecture.domain.lecture.LectureRepository;
import lecture.infrastructure.lecture.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Lecture save(Lecture lecture) {
        return lectureJpaRepository.save(lecture);
    }
}
