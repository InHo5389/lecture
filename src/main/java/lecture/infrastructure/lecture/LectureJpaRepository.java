package lecture.infrastructure.lecture;

import lecture.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<Lecture,Long> {
}
