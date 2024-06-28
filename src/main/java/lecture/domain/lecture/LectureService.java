package lecture.domain.lecture;

import lecture.domain.applyhistory.ApplyHistory;
import lecture.domain.applyhistory.ApplyHistoryRepository;
import lecture.domain.applyhistory.dto.CompleteApplyDto;
import lecture.domain.lecture.dto.ApplyLectureDto;
import lecture.domain.lecture.dto.CreateLectureDto;
import lecture.domain.schedule.Schedule;
import lecture.domain.schedule.ScheduleRepository;
import lecture.domain.user.User;
import lecture.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final ApplyHistoryRepository applyHistoryRepository;
    private final ScheduleRepository scheduleRepository;

    public Lecture createLecture(CreateLectureDto createLectureDto) {
        Lecture savedLecture = lectureRepository.save(createLectureDto.toLectureDomain());
        Schedule savedSchedule = scheduleRepository.save(Schedule.builder()
                .lecture(savedLecture)
                .scheduleDate(createLectureDto.getScheduleDate())
                .scheduleTime(createLectureDto.getScheduleTime())
                .build());
        return savedLecture;
    }

    @Transactional
    public ApplyLectureDto applyLecture(Long userId, Long lectureId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("등록된 유저가 없습니다."));
        Lecture lecture = lectureRepository.findByWithPessimisticLock(lectureId)
                .orElseThrow(() -> new RuntimeException("등록된 강의가 없습니다."));
        Optional<ApplyHistory> optionalApplyHistory = applyHistoryRepository.findByUserIdAndLectureId(user.getId(), lecture.getId());
        if (optionalApplyHistory.isPresent()) {
            throw new RuntimeException("이미 등록한 특강입니다.");
        }
        lecture.apply();
        lectureRepository.save(lecture);

        ApplyHistory applyHistory = applyHistoryRepository.save(new ApplyHistory(user, lecture));
        return new ApplyLectureDto(user, lecture, applyHistory);
    }

    public List<Lecture> getLectures() {
        List<Lecture> lectureList = lectureRepository.findAll();
        for (Lecture lecture : lectureList) {
            List<Schedule> schedules = scheduleRepository.findByLectureId(lecture.getId());
            lecture.setScheduleList(schedules);
        }
        return lectureList;
    }

    public CompleteApplyDto completeApply(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("등록된 유저가 없습니다."));
        ApplyHistory applyHistory = applyHistoryRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("특강 신청을 하나도 성공하지 못했습니다."));
        Lecture lecture = lectureRepository.findById(applyHistory.getLecture().getId()).get();

        return CompleteApplyDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .lectureId(lecture.getId())
                .lectureName(lecture.getName())
                .lectureDescription(lecture.getDescription())
                .build();
    }
}
