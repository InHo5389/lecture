package lecture.domain.schedule;

import lecture.domain.lecture.Lecture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

class ScheduleTest {

    @Test
    @DisplayName("특강 신청 일시보다 신청하는 시간이 후면 true를 리턴한다.")
    void isApplyLectureTrue() {
        //given
        long scheduleId = 1L;
        long lectureId = 1L;
        LocalDateTime applyDateTime = LocalDateTime.of(2024, 06, 28, 13, 00);
        LocalDate scheduleDate = LocalDate.of(2024, 06, 28);
        String scheduleTime = "12:59";
        Lecture lecture = new Lecture(lectureId, "특강", "특강설명", 30, 0, List.of());
        Schedule schedule = new Schedule(scheduleId, lecture, scheduleDate, scheduleTime);
        //when
        boolean result = schedule.isApplyLecture(applyDateTime);
        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("특강 신청 일시보다 신청하는 시간이 전이면 예외가 발생한다..")
    void isApplyLectureFail() {
        //given
        long scheduleId = 1L;
        long lectureId = 1L;
        LocalDateTime applyDateTime = LocalDateTime.of(2024, 06, 28, 13, 00);
        LocalDate scheduleDate = LocalDate.of(2024, 06, 28);
        String scheduleTime = "13:01";
        Lecture lecture = new Lecture(lectureId, "특강", "특강설명", 30, 0, List.of());
        Schedule schedule = new Schedule(scheduleId, lecture, scheduleDate, scheduleTime);
        //when
        //then
        Assertions.assertThatThrownBy(()->schedule.isApplyLecture(applyDateTime))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("특강 신청 시간이 아닙니다.");
    }
}