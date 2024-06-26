package lecture.controller.lecture.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lecture.domain.lecture.Lecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLectureRequest {

    @NotBlank(message = "특강 이름은 필수값입니다.")
    private String name;
    @NotBlank(message = "특강 설명은 필수값입니다.")
    private String description;
    @NotNull(message = "특강 일시는 필수값입니다.")
    private LocalDate lectureDate;
    @NotBlank(message = "특강 시간은 필수값입니다.")
    private String lectureTime;
    @Positive(message = "특강 최대 수용인원은 양수값이어야 합니다.")
    private int maxHeadCount;
    private int applyHeadCount;

    public Lecture toEntity() {
        return Lecture.builder()
                .name(name)
                .description(description)
                .lectureDate(lectureDate)
                .lectureTime(lectureTime)
                .maxHeadCount(maxHeadCount)
                .applyHeadCount(applyHeadCount)
                .build();
    }
}
