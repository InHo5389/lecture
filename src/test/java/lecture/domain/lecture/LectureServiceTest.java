package lecture.domain.lecture;

import lecture.controller.lecture.request.ApplyLectureRequest;
import lecture.controller.lecture.request.CreateLectureRequest;
import lecture.domain.applyhistory.ApplyHistory;
import lecture.domain.applyhistory.ApplyHistoryRepository;
import lecture.domain.schedule.Schedule;
import lecture.domain.schedule.ScheduleRepository;
import lecture.domain.user.User;
import lecture.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private LectureService lectureService;

    @Test
    @DisplayName("특강을 등록할 수 있다.")
    void createLecture() {
        //given
        CreateLectureRequest request = createLectureRequest("알고리즘 강의", "카카오 개발자가 알려주는 명확한 알고리즘",
                LocalDate.of(2024, 06, 25), "13:00", 30, 30);
        Lecture lecture = createLecture(1L, request);
        Schedule schedule = new Schedule(1L, lecture, LocalDate.of(2024, 06, 25), "12:59");
        when(lectureRepository.save(any())).thenReturn(lecture);
        when(scheduleRepository.save(any())).thenReturn(schedule);
        //when
        Lecture savedLecture = lectureService.createLecture(request.toDto());
        //then
        assertThat(savedLecture)
                .extracting("id", "name", "description", "maxHeadCount", "applyHeadCount")
                .contains(savedLecture.getId(), savedLecture.getName(), savedLecture.getDescription()
                        , savedLecture.getMaxHeadCount(), savedLecture.getApplyHeadCount());
    }

    @Test
    @DisplayName("특강을 신청할 때 요청 받은 유저가 없을 경우 예외가 발생한다.")
    void applyWithoutUser() {
        //given
        long userId = 1L;
        long lectureId = 1L;

        ApplyLectureRequest request = new ApplyLectureRequest(userId, lectureId);
        given(userRepository.findById(userId)).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> lectureService.applyLecture(request.getUserId(), request.getLectureId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("등록된 유저가 없습니다.");
    }

    @Test
    @DisplayName("특강을 신청할 때 요청 받은 특강이 없을 경우 예외가 발생한다.")
    void applyWithoutLecture() {
        //given
        long userId = 1L;
        long lectureId = 1L;

        ApplyLectureRequest request = new ApplyLectureRequest(userId, lectureId);
        User user = new User(userId, "인호");
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        //when
        //then
        assertThatThrownBy(() -> lectureService.applyLecture(request.getUserId(), request.getLectureId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("등록된 강의가 없습니다.");
    }

    @Test
    @DisplayName("특강을 신청할수 있다.")
    void applyLecture() {
        //given
        long userId = 1L;
        long lectureId = 1L;

        User user = new User(userId, "인호");
        Lecture lecture = Lecture.builder()
                .maxHeadCount(30)
                .applyHeadCount(0)
                .build();
        ApplyHistory applyHistory = new ApplyHistory(user, lecture);

        ApplyLectureRequest request = new ApplyLectureRequest(userId, lectureId);
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(lectureRepository.findByWithPessimisticLock(anyLong())).willReturn(Optional.of(lecture));
        given(applyHistoryRepository.save(any())).willReturn(applyHistory);
        //when
        //then
        lectureService.applyLecture(request.getUserId(), request.getLectureId());
    }

    @Test
    @DisplayName("강의 목록을 조회할때 스케줄 시간도 같이 조회된다.")
    void getLectures(){
        // Mock 데이터 설정
        Lecture lecture1 = new Lecture(1L, "알고리즘 강의", "알고리즘에 대한 강의입니다.", 2, 0,List.of());
        Lecture lecture2 = new Lecture(2L, "자바 강의", "자바 프로그래밍 강의입니다.", 3, 1,List.of());

        List<Schedule> schedules1 = Arrays.asList(
                new Schedule(1L, lecture1, LocalDate.of(2024, 7, 1), "10:00"),
                new Schedule(2L, lecture1, LocalDate.of(2024, 7, 2), "14:00")
        );

        List<Schedule> schedules2 = Arrays.asList(
                new Schedule(3L, lecture2, LocalDate.of(2024, 7, 5), "09:00"),
                new Schedule(4L, lecture2, LocalDate.of(2024, 7, 6), "15:00")
        );

        when(lectureRepository.findAll()).thenReturn(Arrays.asList(lecture1, lecture2));
        when(scheduleRepository.findByLectureId(1L)).thenReturn(schedules1);
        when(scheduleRepository.findByLectureId(2L)).thenReturn(schedules2);

        List<Lecture> result = lectureService.getLectures();

        verify(lectureRepository, times(1)).findAll();
        verify(scheduleRepository, times(1)).findByLectureId(1L);
        verify(scheduleRepository, times(1)).findByLectureId(2L);

        // 결과 검증
        // 예시로 강의 리스트의 크기와 각각의 강의의 스케줄 리스트 크기 등을 검증할 수 있습니다.
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getScheduleList()).hasSize(2);
        assertThat(result.get(1).getScheduleList()).hasSize(2);
    }

    private Lecture createLecture(long id, CreateLectureRequest request) {
        return Lecture.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
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