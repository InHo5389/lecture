package lecture.controller.lecture.response;

import lecture.domain.applyhistory.dto.CompleteApplyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteApplyLectureResponse {

    private Long userId;
    private String name;
    private Long lectureId;
    private String lectureName;
    private String lectureDescription;

    public static CompleteApplyLectureResponse of(CompleteApplyDto dto){
        return CompleteApplyLectureResponse.builder()
                .userId(dto.getUserId())
                .name(dto.getName())
                .lectureId(dto.getLectureId())
                .lectureName(dto.getLectureName())
                .lectureDescription(dto.getLectureDescription())
                .build();
    }
}
