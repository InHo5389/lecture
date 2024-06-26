package lecture.controller.lecture;

import jakarta.validation.Valid;
import lecture.common.exception.ApiResponse;
import lecture.controller.lecture.request.CreateLectureRequest;
import lecture.controller.lecture.response.CreateLectureResponse;
import lecture.domain.lecture.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @PostMapping("/lectures")
    public ApiResponse<CreateLectureResponse> createLecture(@RequestBody @Valid CreateLectureRequest request) {
        return ApiResponse.ok(lectureService.createLecture(request));
    }
}
