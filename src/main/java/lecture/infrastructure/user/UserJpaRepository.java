package lecture.infrastructure.user;

import lecture.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User,Long> {
}
