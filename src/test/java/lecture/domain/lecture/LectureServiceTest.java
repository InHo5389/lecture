package lecture.domain.lecture;

import lecture.controller.lecture.request.ApplyLectureRequest;
import lecture.controller.lecture.request.CreateLectureRequest;
import lecture.controller.lecture.response.CreateLectureResponse;
import lecture.domain.lecture.applyhistory.ApplyHistory;
import lecture.domain.lecture.applyhistory.ApplyHistoryRepository;
import lecture.domain.user.User;
import lecture.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplyHistoryRepository applyHistoryRepository;

    @InjectMocks
    private LectureService lectureService;

    @Test
    @DisplayName("특강을 등록할 수 있다.")
    void createLecture(){
        //given
        CreateLectureRequest request = createLectureRequest("알고리즘 강의", "카카오 개발자가 알려주는 명확한 알고리즘",
                LocalDate.of(2024, 06, 25), "13:00", 30, 30);
        Lecture lecture = createLecture(1L, request);
        when(lectureRepository.save(any())).thenReturn(lecture);
        //when
        Lecture savedLecture = lectureService.createLecture(request.toEntity());
        //then
        assertThat(savedLecture)
                .extracting("id", "name", "description", "lectureDate", "lectureTime", "maxHeadCount", "applyHeadCount")
                .contains(savedLecture.getId(), savedLecture.getName(), savedLecture.getDescription(), savedLecture.getLectureDate()
                        , savedLecture.getLectureTime(), savedLecture.getMaxHeadCount(), savedLecture.getApplyHeadCount());
    }

    @Test
    @DisplayName("특강을 신청할 때 요청 받은 유저가 없을 경우 예외가 발생한다.")
    void applyWithoutUser(){
        //given
        long userId = 1L;
        long lectureId = 1L;
        LocalDateTime now = LocalDateTime.now();

        ApplyLectureRequest request = new ApplyLectureRequest(userId, lectureId);
        given(userRepository.findById(userId)).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(()->lectureService.applyLecture(request.getUserId(),request.getLectureId(),now))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("등록된 유저가 없습니다.");
    }

    @Test
    @DisplayName("특강을 신청할 때 요청 받은 특강이 없을 경우 예외가 발생한다.")
    void applyWithoutLecture(){
        //given
        long userId = 1L;
        long lectureId = 1L;
        LocalDateTime now = LocalDateTime.now();

        ApplyLectureRequest request = new ApplyLectureRequest(userId, lectureId);
        User user = new User(userId, "인호");
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        //when
        //then
        assertThatThrownBy(()->lectureService.applyLecture(request.getUserId(),request.getLectureId(),now))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("등록된 강의가 없습니다.");
    }

    @Test
    @DisplayName("특강을 신청할수 있다.")
    void applyLecture(){
        //given
        long userId = 1L;
        long lectureId = 1L;
        LocalDateTime now = LocalDateTime.now();

        User user = new User(userId, "인호");
        Lecture lecture = Lecture.builder()
                .lectureDate(LocalDate.of(2024, 06, 01))
                .lectureTime("13:00")
                .maxHeadCount(30)
                .applyHeadCount(0)
                .build();
        ApplyHistory applyHistory = new ApplyHistory(userId, lectureId);

        ApplyLectureRequest request = new ApplyLectureRequest(userId, lectureId);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(lectureRepository.findByWithPessimisticLock(anyLong())).willReturn(Optional.of(lecture));
        given(applyHistoryRepository.save(any())).willReturn(applyHistory);
        //when
        //then
        lectureService.applyLecture(request.getUserId(),request.getLectureId(),now);
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

    private CreateLectureRequest createLectureRequest(String name, String description, LocalDate lectureDate, String lectureTime, int maxHeadCount, int applyHeadCount) {
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