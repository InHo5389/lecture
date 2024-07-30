package lecture.controller.lecture.response;

import lecture.domain.lecture.Lecture;
import lecture.domain.schedule.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetLectureResponse {

    private Long id;
    private String name;
    private String description;
    private int maxHeadCount;
    private int applyHeadCount;
    private List<LocalDate> scheduleDate;
    private List<String> scheduleTime;

    public static GetLectureResponse of(Lecture lecture){
        return GetLectureResponse.builder()
                .id(lecture.getId())
                .name(lecture.getName())
                .description(lecture.getDescription())
                .maxHeadCount(lecture.getMaxHeadCount())
                .applyHeadCount(lecture.getApplyHeadCount())
                .scheduleDate(lecture.getScheduleList().stream().map(schedule -> schedule.getScheduleDate()).collect(Collectors.toList()))
                .scheduleTime(lecture.getScheduleList().stream().map(schedule -> schedule.getScheduleTime()).collect(Collectors.toList()))
                .build();
    }
}
