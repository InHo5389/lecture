package lecture.infrastructure.lecture.applyhistory;

import jakarta.persistence.*;
import lecture.domain.lecture.applyhistory.ApplyHistory;
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

    private Long userId;
    private Long lectureId;

    @CreatedDate
    private LocalDateTime createAt;

    public ApplyHistoryEntity(Long userId, Long lectureId) {
        this.userId = userId;
        this.lectureId = lectureId;
    }

    public ApplyHistory toDomain() {
        return ApplyHistory.builder()
                .id(id)
                .userId(userId)
                .lectureId(lectureId)
                .build();
    }

    public static ApplyHistoryEntity from(ApplyHistory applyHistory) {
        ApplyHistoryEntity applyHistoryEntity = new ApplyHistoryEntity();
        applyHistoryEntity.id = applyHistoryEntity.getId();
        applyHistoryEntity.userId = applyHistory.getUserId();
        applyHistoryEntity.lectureId = applyHistory.getLectureId();
        return applyHistoryEntity;
    }
}
