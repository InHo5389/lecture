package lecture.domain.lecture;

import lecture.controller.lecture.request.ApplyLectureRequest;
import lecture.domain.applyhistory.ApplyHistory;
import lecture.domain.applyhistory.ApplyHistoryRepository;
import lecture.domain.user.User;
import lecture.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class LectureIntegrateServiceTest {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplyHistoryRepository applyHistoryRepository;

    @Autowired
    private LectureService lectureService;

    @AfterEach
    void tearDown(){
        applyHistoryRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("특강을 신청할때 동시에 30번 신청을 하면 신청인원이 30명이어야 한다.")
    void applyLectureFullApplyHeadCount() throws InterruptedException {
        //given
        long userId = 1L;
        long lectureId = 1L;
        int threadCount = 30;
        for (int i = 0; i < threadCount; i++) {
            userRepository.save(new User((long) i + 1, "인호" + i));
        }
        Lecture lecture = Lecture.builder()
                .id(lectureId)
                .name("알고리즘 강의")
                .description("카카오 개발자가 알려주는 명확한 알고리즘")
                .maxHeadCount(30)
                .applyHeadCount(0)
                .build();
        Lecture lecture1 = lectureRepository.save(lecture);
        System.out.println("**************************"+lecture1.getMaxHeadCount());
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long localUserId = userId + i;
            executorService.submit(()->{
                try {
                    lectureService.applyLecture(localUserId,lectureId);
                }catch (Exception e){
                    System.out.println("---------------------------------------");
                }finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        //when
        Lecture savedLecture = lectureRepository.findById(lectureId).get();
        System.out.println(savedLecture.getMaxHeadCount()+"*/******************************************");
        //then
        assertThat(savedLecture.getApplyHeadCount()).isEqualTo(30);
    }

    @Test
    @DisplayName("특강을 신청할때 최대 수용 인원수를 넘어서 특강 신청을 하면 예외가 발생해야한다.")
    void applyLectureWithConcurrency() throws InterruptedException {
        //given
        AtomicBoolean exceptionThrown = new AtomicBoolean(false);
        long userId = 1L;
        long lectureId = 1L;
        int maxHeadCount = 4;
        int threadCount = 5;

        for (int i = 0; i < threadCount; i++) {
            userRepository.save(new User(userId + i, "인호" + i));
        }
        Lecture lecture = Lecture.builder()
                .id(lectureId)
                .name("알고리즘 강의")
                .description("카카오 개발자가 알려주는 명확한 알고리즘")
                .maxHeadCount(maxHeadCount)
                .applyHeadCount(0)
                .build();
        lectureRepository.save(lecture);
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);


        for (int i = 0; i < threadCount; i++) {
            long localUserId = userId + i;
            executorService.submit(() -> {
                try {
                    lectureService.applyLecture(localUserId,lectureId);
                } catch (RuntimeException e) {
                    System.out.println("예외 발생");
                    exceptionThrown.set(true);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        //when
        //then
        Assertions.assertThat(exceptionThrown).isTrue();
    }

    @Test
    @DisplayName("특강을 신청할때 같은 유저가 똑같은 특강을 신청을 하면 예외가 발생하여야 한다.")
    void applyLectureSameUser() throws InterruptedException {
        //given
        long userId = 1L;
        long lectureId = 1L;

        User user = userRepository.save(new User(userId, "인호"));

        Lecture lecture = Lecture.builder()
                .id(lectureId)
                .name("알고리즘 강의")
                .description("카카오 개발자가 알려주는 명확한 알고리즘")
                .maxHeadCount(30)
                .applyHeadCount(0)
                .build();
        lectureRepository.save(lecture);

        applyHistoryRepository.save(new ApplyHistory(1L, user, lecture, LocalDateTime.now()));
        //when
        //then
        assertThatThrownBy(()->lectureService.applyLecture(userId, lectureId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("이미 등록한 특강입니다.");
    }

    private Lecture createLecture(long lectureId, String name, String description, LocalDate lectureDate, String lectureTime, int maxHeadCount, int applyHeadCount) {
        return Lecture.builder()
                .id(lectureId)
                .name(name)
                .description(description)
                .maxHeadCount(maxHeadCount)
                .applyHeadCount(applyHeadCount)
                .build();
    }
}
