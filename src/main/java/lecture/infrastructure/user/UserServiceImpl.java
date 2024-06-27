package lecture.infrastructure.user;

import lecture.domain.user.User;
import lecture.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserServiceImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toDomain();
    }

    @Override
    public Optional<User> findById(long userId) {
        return userJpaRepository.findById(userId).map(UserEntity::toDomain);
    }
}
