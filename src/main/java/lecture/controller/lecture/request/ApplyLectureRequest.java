package lecture.controller.lecture.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyLectureRequest {

    private Long userId;
    private Long lectureId;
}
