package lecture.infrastructure.lecture;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lecture.domain.lecture.Lecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int maxHeadCount;
    private int applyHeadCount;

    public Lecture toDomain(){
        return Lecture.builder()
                .id(id)
                .name(name)
                .description(description)
                .maxHeadCount(maxHeadCount)
                .applyHeadCount(applyHeadCount)
                .build();
    }

    public static LectureEntity from(Lecture lecture) {
        LectureEntity lectureEntity = new LectureEntity();
        lectureEntity.id = lecture.getId();
        lectureEntity.name = lecture.getName();
        lectureEntity.description = lecture.getDescription();
        lectureEntity.maxHeadCount = lecture.getMaxHeadCount();
        lectureEntity.applyHeadCount = lecture.getApplyHeadCount();
        return lectureEntity;
    }
}
