package lecture.domain.applyhistory;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lecture.domain.lecture.Lecture;
import lecture.domain.user.User;
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

    private User user;
    private Lecture lecture;
    private LocalDateTime createAt;

    public ApplyHistory(User user, Lecture lecture) {
        this.user = user;
        this.lecture = lecture;
    }
}
