package lecture.domain.user;

import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(long userId);
}
