package lecture.infrastructure.schedule;

import lecture.domain.schedule.Schedule;
import lecture.domain.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleJpaRepository scheduleJpaRepository;

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleJpaRepository.save(ScheduleEntity.from(schedule)).toDomain();
    }

    @Override
    public Optional<Schedule> findById(long scheduleId) {
        return scheduleJpaRepository.findById(scheduleId).map(ScheduleEntity::toDomain);
    }

    @Override
    public List<Schedule> findByLectureId(long lectureId) {
        return scheduleJpaRepository.findByLectureId(lectureId).stream().map(ScheduleEntity::toDomain).toList();
    }
}
