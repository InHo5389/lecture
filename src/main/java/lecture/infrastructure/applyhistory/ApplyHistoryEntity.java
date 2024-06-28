package lecture.infrastructure.applyhistory;

import jakarta.persistence.*;
import lecture.domain.applyhistory.ApplyHistory;
import lecture.domain.lecture.Lecture;
import lecture.domain.user.User;
import lecture.infrastructure.lecture.LectureEntity;
import lecture.infrastructure.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ApplyHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    private LectureEntity lecture;

    @CreatedDate
    private LocalDateTime createAt;

    public ApplyHistoryEntity(UserEntity user, LectureEntity lecture) {
        this.user = user;
        this.lecture = lecture;
    }

    public ApplyHistory toDomain() {
        return ApplyHistory.builder()
                .id(id)
                .user(user.toDomain())
                .lecture(lecture.toDomain())
                .build();
    }

    public static ApplyHistoryEntity from(ApplyHistory applyHistory) {
        ApplyHistoryEntity applyHistoryEntity = new ApplyHistoryEntity();
        applyHistoryEntity.id = applyHistoryEntity.getId();
        applyHistoryEntity.user = UserEntity.from(applyHistory.getUser());
        applyHistoryEntity.lecture = LectureEntity.from(applyHistory.getLecture());
        return applyHistoryEntity;
    }
}
