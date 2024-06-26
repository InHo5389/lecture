package lecture.domain.lecture;

import lecture.controller.lecture.request.CreateLectureRequest;
import lecture.controller.lecture.response.CreateLectureResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    @Test
    @DisplayName("특강을 등록할 수 있다.")
    void createLecture(){
        //given
        CreateLectureRequest request = createRequest("알고리즘 강의", "카카오 개발자가 알려주는 명확한 알고리즘",
                LocalDate.of(2024, 06, 25), "13:00", 30, 30);
        Lecture lecture = createLecture(1L, request);
        when(lectureRepository.save(any())).thenReturn(lecture);
        //when
        CreateLectureResponse response = lectureService.createLecture(request);
        //then
        assertThat(response)
                .extracting("id", "name", "description", "lectureDate", "lectureTime", "maxHeadCount", "applyHeadCount")
                .contains(response.getId(), response.getName(), response.getDescription(), response.getLectureDate()
                        , response.getLectureTime(), response.getMaxHeadCount(), response.getApplyHeadCount());
    }

    private Lecture createLecture(long id, CreateLectureRequest request) {
        return Lecture.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .lectureDate(request.getLectureDate())
                .lectureTime(request.getLectureTime())
                .maxHeadCount(request.getMaxHeadCount())
                .applyHeadCount(request.getApplyHeadCount())
                .build();
    }

    private CreateLectureRequest createRequest(String name, String description, LocalDate lectureDate, String lectureTime, int maxHeadCount, int applyHeadCount) {
        return CreateLectureRequest.builder()
                .name(name)
                .description(description)
                .lectureDate(lectureDate)
                .lectureTime(lectureTime)
                .maxHeadCount(maxHeadCount)
                .applyHeadCount(applyHeadCount)
                .build();
    }
}