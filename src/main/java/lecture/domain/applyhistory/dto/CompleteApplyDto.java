package lecture.domain.applyhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteApplyDto {

    private Long userId;
    private String name;
    private Long lectureId;
    private String lectureName;
    private String lectureDescription;
}
