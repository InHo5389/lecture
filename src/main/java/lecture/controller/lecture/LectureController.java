package lecture.controller.lecture;

import jakarta.validation.Valid;
import lecture.common.exception.ApiResponse;
import lecture.controller.lecture.request.ApplyLectureRequest;
import lecture.controller.lecture.request.CreateLectureRequest;
import lecture.controller.lecture.response.CompleteApplyLectureResponse;
import lecture.controller.lecture.response.CreateLectureResponse;
import lecture.controller.lecture.response.GetLectureResponse;
import lecture.domain.applyhistory.dto.CompleteApplyDto;
import lecture.domain.lecture.Lecture;
import lecture.domain.lecture.LectureService;
import lecture.domain.lecture.dto.ApplyLectureDto;
import lecture.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;
    // todo 삭제
    private final UserService userService;

    @PostMapping("/lectures")
    public ApiResponse<CreateLectureResponse> createLecture(@RequestBody @Valid CreateLectureRequest request) {
        Lecture lecture = lectureService.createLecture(request.toDto());
        return ApiResponse.ok(CreateLectureResponse.of(lecture));
    }

    @PostMapping("/lectures/apply")
    public ApiResponse<ApplyLectureDto> applyLecture(@RequestBody ApplyLectureRequest request) {

        return ApiResponse.ok(lectureService.applyLecture(request.getUserId(), request.getLectureId()));
    }

    @GetMapping("/lectures")
    public ApiResponse<List<GetLectureResponse>> getLectures() {
        List<Lecture> lectures = lectureService.getLectures();
        List<GetLectureResponse> responses = lectures.stream().map(lecture -> GetLectureResponse.of(lecture)).collect(Collectors.toList());
        return ApiResponse.ok(responses);
    }

    @GetMapping("/lectures/application/{userId}")
    public ApiResponse<CompleteApplyLectureResponse> completeApply(@PathVariable Long userId) {
        CompleteApplyDto completeApplyDto = lectureService.completeApply(userId);
        return ApiResponse.ok(CompleteApplyLectureResponse.of(completeApplyDto));
    }

    // todo 삭제
    @PostMapping("/users")
    public void saveUser(@RequestBody String name) {
        userService.saveUser(name);
    }
}
