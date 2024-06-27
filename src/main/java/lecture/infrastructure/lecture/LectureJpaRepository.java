package lecture.infrastructure.lecture;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LectureJpaRepository extends JpaRepository<LectureEntity,Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from LectureEntity l where l.id = :id")
    Optional<LectureEntity> findByWithPessimisticLock(Long id);
}
