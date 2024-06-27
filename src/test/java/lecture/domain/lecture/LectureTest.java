package lecture.domain.lecture;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


class LectureTest {

    @Test
    @DisplayName("특강신청시간이 지나지 않았으면 예외가 일어난다.")
    void applyIsBeforeLectureDate() {
        //given
        Lecture lecture = Lecture.builder()
                .maxHeadCount(30)
                .applyHeadCount(0)
                .lectureDate(LocalDate.of(2024, 06, 01))
                .lectureTime("13:00")
                .build();
        LocalDateTime now = LocalDateTime.of(2024, 06, 01, 12, 59);
        //when
        //then
        assertThatThrownBy(() -> lecture.apply(now))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("특강 신청 시간이 아닙니다.");
    }

    @Test
    @DisplayName("특강 수강인원이 초과되면 예외가 발생한다.")
    void applyIsPullApplyHeadCount() {
        //given
        Lecture lecture = Lecture.builder()
                .lectureDate(LocalDate.of(2024, 06, 01))
                .lectureTime("13:00")
                .maxHeadCount(30)
                .applyHeadCount(30)
                .build();
        LocalDateTime now = LocalDateTime.of(2024, 06, 01, 13, 01);
        //when
        //then
        assertThatThrownBy(() -> lecture.apply(now))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("특강 수강 인원이 초과되었습니다.");
    }

    @Test
    @DisplayName("특강을 신청하면 신청인원수가 한명 늘어난다.")
    void apply() {
        //given
        int applyHeadCount = 0;
        Lecture lecture = Lecture.builder()
                .lectureDate(LocalDate.of(2024, 06, 01))
                .lectureTime("13:00")
                .maxHeadCount(30)
                .applyHeadCount(applyHeadCount)
                .build();
        LocalDateTime now = LocalDateTime.of(2024, 06, 01, 13, 01);
        //when
        lecture.apply(now);
        //then
        assertThat(lecture.getApplyHeadCount()).isEqualTo(applyHeadCount + 1);
    }
}