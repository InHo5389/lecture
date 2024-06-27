package lecture.domain.lecture.applyhistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyHistory {

    private Long id;

    private Long userId;
    private Long lectureId;
    private LocalDateTime createAt;

    public ApplyHistory(Long userId, Long lectureId) {
        this.userId = userId;
        this.lectureId = lectureId;
    }
}
