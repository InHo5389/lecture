package lecture.domain.lecture.dto;

import lecture.domain.lecture.Lecture;
import lecture.domain.lecture.applyhistory.ApplyHistory;
import lecture.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyLectureDto {

    private User user;
    private Lecture lecture;
    private ApplyHistory applyHistory;
}
