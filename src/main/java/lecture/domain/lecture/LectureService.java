package lecture.domain.lecture;

import lecture.domain.lecture.applyhistory.ApplyHistory;
import lecture.domain.lecture.applyhistory.ApplyHistoryRepository;
import lecture.domain.lecture.dto.ApplyLectureDto;
import lecture.domain.user.User;
import lecture.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final ApplyHistoryRepository applyHistoryRepository;

    public Lecture createLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    @Transactional
    public ApplyLectureDto applyLecture(Long userId,Long lectureId, LocalDateTime now) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("등록된 유저가 없습니다."));
        Lecture lecture = lectureRepository.findByWithPessimisticLock(lectureId)
                .orElseThrow(() -> new RuntimeException("등록된 강의가 없습니다."));
        Optional<ApplyHistory> optionalApplyHistory = applyHistoryRepository.findByUserIdAndLectureId(user.getId(), lecture.getId());
        if (optionalApplyHistory.isPresent()) {
            throw new RuntimeException("이미 등록한 특강입니다.");
        }

        lecture.apply(now);
        lectureRepository.save(lecture);

        ApplyHistory applyHistory = applyHistoryRepository.save(new ApplyHistory(user.getId(), lecture.getId()));
        return new ApplyLectureDto(user,lecture,applyHistory);
    }
}
