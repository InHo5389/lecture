package lecture.domain.lecture;

import lecture.domain.schedule.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lecture {

    private Long id;
    private String name;
    private String description;
    private int maxHeadCount;
    private int applyHeadCount;
    private List<Schedule> scheduleList;


    public boolean apply(){
        boolean applyResult = true;

        if (applyHeadCount >= maxHeadCount){
            throw new RuntimeException("특강 수강 인원이 초과되었습니다.");
        }
        applyHeadCount++;

        return applyResult;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }
}


