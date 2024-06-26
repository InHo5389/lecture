package lecture.domain.lecture;

import lecture.controller.lecture.request.CreateLectureRequest;
import lecture.controller.lecture.response.CreateLectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    public CreateLectureResponse createLecture(CreateLectureRequest request) {
        Lecture lecture = lectureRepository.save(request.toEntity());
        return CreateLectureResponse.of(lecture);
    }
}
