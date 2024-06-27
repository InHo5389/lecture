package lecture.domain.lecture;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDate lectureDate;
    private String lectureTime;
    private int maxHeadCount;
    private int applyHeadCount;

    public void apply(LocalDateTime now){
        LocalDateTime lectureDateTime = getLocalDateTime();
        if (lectureDateTime.isAfter(now)){
            throw new RuntimeException("특강 신청 시간이 아닙니다.");
        }

        if (applyHeadCount >= maxHeadCount){
            throw new RuntimeException("특강 수강 인원이 초과되었습니다.");
        }
        applyHeadCount++;
    }

    private LocalDateTime getLocalDateTime(){
        LocalTime time = LocalTime.parse(lectureTime, DateTimeFormatter.ofPattern("HH:mm"));
        return LocalDateTime.of(lectureDate,time);
    }
}
