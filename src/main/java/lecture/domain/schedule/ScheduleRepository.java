package lecture.domain.schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);
    Optional<Schedule> findById(long scheduleId);
    List<Schedule> findByLectureId(long lectureId);
}
