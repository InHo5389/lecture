package lecture.controller.lecture;

import jakarta.validation.Valid;
import lecture.common.exception.ApiResponse;
import lecture.controller.lecture.request.ApplyLectureRequest;
import lecture.controller.lecture.request.CreateLectureRequest;
import lecture.controller.lecture.response.ApplyLectureResponse;
import lecture.controller.lecture.response.CreateLectureResponse;
import lecture.domain.lecture.Lecture;
import lecture.domain.lecture.LectureService;
import lecture.domain.lecture.dto.ApplyLectureDto;
import lecture.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;
    // todo 삭제
    private final UserService userService;

    @PostMapping("/lectures")
    public ApiResponse<CreateLectureResponse> createLecture(@RequestBody @Valid CreateLectureRequest request) {
        Lecture lecture = lectureService.createLecture(request.toEntity());
        return ApiResponse.ok(CreateLectureResponse.of(lecture));
    }

    @PostMapping("/lectures/apply")
    public ApiResponse<ApplyLectureResponse> applyLecture(@RequestBody ApplyLectureRequest request) {
        LocalDateTime now = LocalDateTime.now();
        ApplyLectureDto dto = lectureService.applyLecture(request.getUserId(), request.getLectureId(), now);
        return ApiResponse.ok(ApplyLectureResponse.of(dto.getUser(), dto.getLecture(), dto.getApplyHistory()));
    }

    // todo 삭제
    @PostMapping("/users")
    public void saveUser(@RequestBody String name){
        userService.saveUser(name);
    }
}
