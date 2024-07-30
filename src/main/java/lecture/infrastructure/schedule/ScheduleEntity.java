package lecture.infrastructure.schedule;

import jakarta.persistence.*;
import lecture.domain.lecture.Lecture;
import lecture.domain.schedule.Schedule;
import lecture.domain.user.User;
import lecture.infrastructure.lecture.LectureEntity;
import lecture.infrastructure.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private LectureEntity lecture;
    private LocalDate scheduleDate;
    private String scheduleTime;

    public Schedule toDomain(){
        return new Schedule(id,lecture.toDomain(),scheduleDate,scheduleTime);
    }

    public static ScheduleEntity from(Schedule schedule) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.id = schedule.getId();
        scheduleEntity.lecture = LectureEntity.from(schedule.getLecture());
        scheduleEntity.scheduleDate = schedule.getScheduleDate();
        scheduleEntity.scheduleTime = schedule.getScheduleTime();
        return scheduleEntity;
    }
}
