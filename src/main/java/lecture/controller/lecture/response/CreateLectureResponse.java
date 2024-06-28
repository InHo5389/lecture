package lecture.controller.lecture.response;

import lecture.domain.lecture.Lecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLectureResponse {

    private Long id;
    private String name;
    private String description;
    private int maxHeadCount;
    private int applyHeadCount;

    public static CreateLectureResponse of(Lecture lecture) {
        return CreateLectureResponse.builder()
                .id(lecture.getId())
                .name(lecture.getName())
                .description(lecture.getDescription())
                .maxHeadCount(lecture.getMaxHeadCount())
                .applyHeadCount(lecture.getApplyHeadCount())
                .build();
    }
}
