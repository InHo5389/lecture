package lecture.infrastructure.lecture;

import jakarta.persistence.LockModeType;
import lecture.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LectureJpaRepository extends JpaRepository<Lecture,Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from Lecture l where l.id = :id")
    Optional<Lecture> findByWithPessimisticLock(Long id);
}
