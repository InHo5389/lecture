package lecture.controller.lecture.response;

import lecture.domain.lecture.Lecture;
import lecture.domain.lecture.applyhistory.ApplyHistory;
import lecture.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyLectureResponse {

    private Long userId;
    private String name;
    private Long lectureId;
    private String lectureName;
    private String description;
    private LocalDate lectureDate;
    private String lectureTime;
    private LocalDateTime createdAt;

    public static ApplyLectureResponse of(User user, Lecture lecture, ApplyHistory applyHistory) {
        return ApplyLectureResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .lectureId(lecture.getId())
                .lectureName(lecture.getName())
                .description(lecture.getDescription())
                .lectureDate(lecture.getLectureDate())
                .lectureTime(lecture.getLectureTime())
                .createdAt(applyHistory.getCreateAt())
                .build();
    }
}
