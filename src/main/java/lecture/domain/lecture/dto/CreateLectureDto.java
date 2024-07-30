package lecture.domain.lecture.dto;

import lecture.domain.lecture.Lecture;
import lecture.domain.schedule.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLectureDto {

    private String name;
    private String description;
    private int maxHeadCount;
    private int applyHeadCount;
    private LocalDate scheduleDate;
    private String scheduleTime;

    public Lecture toLectureDomain(){
        return Lecture.builder()
                .name(name)
                .description(description)
                .maxHeadCount(maxHeadCount)
                .applyHeadCount(applyHeadCount)
                .build();
    }
}
