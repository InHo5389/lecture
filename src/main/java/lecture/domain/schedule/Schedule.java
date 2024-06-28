package lecture.domain.schedule;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lecture.domain.lecture.Lecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    private Long id;
    private Lecture lecture;
    private LocalDate scheduleDate;
    private String scheduleTime;

    public boolean isApplyLecture(LocalDateTime now){
        boolean isAvailable = true;

        LocalDateTime lectureDateTime = getLocalDateTime();
        if (lectureDateTime.isAfter(now)) {
            throw new RuntimeException("특강 신청 시간이 아닙니다.");
        }

        return isAvailable;
    }

    private LocalDateTime getLocalDateTime() {
        LocalTime time = LocalTime.parse(scheduleTime, DateTimeFormatter.ofPattern("HH:mm"));
        return LocalDateTime.of(scheduleDate, time);
    }
}
