package lecture.domain.lecture;

import lecture.controller.lecture.request.ApplyLectureRequest;
import lecture.controller.lecture.request.CreateLectureRequest;
import lecture.controller.lecture.response.ApplyLectureResponse;
import lecture.controller.lecture.response.CreateLectureResponse;
import lecture.domain.lecture.applyhistory.ApplyHistory;
import lecture.domain.lecture.applyhistory.ApplyHistoryRepository;
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

    public CreateLectureResponse createLecture(CreateLectureRequest request) {
        Lecture lecture = lectureRepository.save(request.toEntity());
        return CreateLectureResponse.of(lecture);
    }

    @Transactional
    public ApplyLectureResponse applyLecture(ApplyLectureRequest request, LocalDateTime now) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("등록된 유저가 없습니다."));
        Lecture lecture = lectureRepository.findByWithPessimisticLock(request.getLectureId())
                .orElseThrow(() -> new RuntimeException("등록된 강의가 없습니다."));
        Optional<ApplyHistory> applyHistory = applyHistoryRepository.findByUserIdAndLectureId(user.getId(), lecture.getId());
        if (applyHistory.isPresent()){
            throw new RuntimeException("이미 등록한 특강입니다.");
        }
        lecture.apply(now);
        ApplyHistory savedApplyHistory = applyHistoryRepository.save(new ApplyHistory(user.getId(), lecture.getId()));
        return ApplyLectureResponse.of(user,lecture,savedApplyHistory);
    }
}
