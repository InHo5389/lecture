package lecture.controller.lecture;

import com.fasterxml.jackson.databind.ObjectMapper;
import lecture.controller.lecture.request.ApplyLectureRequest;
import lecture.controller.lecture.request.CreateLectureRequest;
import lecture.controller.lecture.response.GetLectureResponse;
import lecture.domain.applyhistory.ApplyHistory;
import lecture.domain.lecture.Lecture;
import lecture.domain.lecture.LectureService;
import lecture.domain.lecture.dto.ApplyLectureDto;
import lecture.domain.user.User;
import lecture.domain.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LectureController.class)
class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LectureService lectureService;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("특강을 등록할 수 있다..")
    void createLecture() throws Exception {
        //given
        CreateLectureRequest request = CreateLectureRequest.builder()
                .name("알고리즘 강의")
                .description("카카오 개발자가 알려주는 명확한 알고리즘")
                .lectureDate(LocalDate.of(2024,06,24))
                .lectureTime("13:00")
                .maxHeadCount(30)
                .build();

        given(lectureService.createLecture(any())).willReturn(request.toEntity());
        //when
        //then
        mockMvc.perform(post("/lectures")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("특강을 등록할 때 name 필드가 빠지면 BindException 예외가 발생한다.")
    void createLectureWithOutName() throws Exception {
        //given
        CreateLectureRequest request = CreateLectureRequest.builder()
                .description("카카오 개발자가 알려주는 명확한 알고리즘")
                .lectureDate(LocalDate.of(2024,06,24))
                .lectureTime("13:00")
                .maxHeadCount(30)
                .build();
        //when
        //then
        mockMvc.perform(
                        post("/lectures")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("바인딩 오류"))
                .andExpect(jsonPath("$.data.name").value("특강 이름은 필수값입니다."));
    }

    @Test
    @DisplayName("특강을 등록할 때 description 필드가 빠지면 BindException 예외가 발생한다.")
    void createLectureWithOutDescription() throws Exception {
        //given
        CreateLectureRequest request = CreateLectureRequest.builder()
                .name("알고리즘 강의")
                .lectureDate(LocalDate.of(2024,06,24))
                .lectureTime("13:00")
                .maxHeadCount(30)
                .build();
        //when
        //then
        mockMvc.perform(
                        post("/lectures")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("바인딩 오류"))
                .andExpect(jsonPath("$.data.description").value("특강 설명은 필수값입니다."));
    }
    @Test
    @DisplayName("특강을 등록할 때 lectureDate 필드가 빠지면 BindException 예외가 발생한다.")
    void createLectureWithOutLectureDate() throws Exception {
        //given
        CreateLectureRequest request = CreateLectureRequest.builder()
                .name("알고리즘 강의")
                .description("카카오 개발자가 알려주는 명확한 알고리즘")
                .lectureTime("13:00")
                .maxHeadCount(30)
                .build();
        //when
        //then
        mockMvc.perform(
                        post("/lectures")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("바인딩 오류"))
                .andExpect(jsonPath("$.data.lectureDate").value("특강 일시는 필수값입니다."));
    }
    @Test
    @DisplayName("특강을 등록할 때 lectureTime 필드가 빠지면 BindException 예외가 발생한다.")
    void createLectureWithOutLectureTime() throws Exception {
        //given
        CreateLectureRequest request = CreateLectureRequest.builder()
                .name("알고리즘 강의")
                .description("카카오 개발자가 알려주는 명확한 알고리즘")
                .lectureDate(LocalDate.of(2024,06,24))
                .maxHeadCount(30)
                .build();
        //when
        //then
        mockMvc.perform(
                        post("/lectures")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("바인딩 오류"))
                .andExpect(jsonPath("$.data.lectureTime").value("특강 시간은 필수값입니다."));
    }
    @Test
    @DisplayName("특강을 등록할 때 maxCapacity 필드가 음수면 BindException 예외가 발생한다.")
    void createLectureWithOutMaxCapacity() throws Exception {
        //given
        CreateLectureRequest request = CreateLectureRequest.builder()
                .name("알고리즘 강의")
                .description("카카오 개발자가 알려주는 명확한 알고리즘")
                .lectureDate(LocalDate.of(2024,06,24))
                .lectureTime("13:00")
                .maxHeadCount(-1)
                .build();
        //when
        //then
        mockMvc.perform(
                        post("/lectures")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("바인딩 오류"))
                .andExpect(jsonPath("$.data.maxHeadCount").value("특강 최대 수용인원은 양수값이어야 합니다."));
    }

    @Test
    @DisplayName("특강을 등록할 때 maxCapacity 필드가 0이면 BindException 예외가 발생한다.")
    void createLectureWithMaxCapacityZero() throws Exception {
        //given
        CreateLectureRequest request = CreateLectureRequest.builder()
                .name("알고리즘 강의")
                .description("카카오 개발자가 알려주는 명확한 알고리즘")
                .lectureDate(LocalDate.of(2024,06,24))
                .lectureTime("13:00")
                .maxHeadCount(0)
                .build();
        //when
        //then
        mockMvc.perform(
                        post("/lectures")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("바인딩 오류"))
                .andExpect(jsonPath("$.data.maxHeadCount").value("특강 최대 수용인원은 양수값이어야 합니다."));
    }

    @Test
    @DisplayName("특강을 신청할 수 있다.")
    void applyLecture() throws Exception {
        //given
        long userId = 1L;
        long lectureId = 1L;
        ApplyLectureRequest request = new ApplyLectureRequest(userId,lectureId);
        User user = new User(userId, "인호");

        Lecture lecture = Lecture.builder()
                .id(lectureId)
                .name("강의")
                .description("강의")
                .maxHeadCount(30)
                .applyHeadCount(0)
                .build();
        ApplyLectureDto dto = new ApplyLectureDto(new User(userId,"인호"),lecture,new ApplyHistory(user,lecture));
        given(lectureService.applyLecture(anyLong(),anyLong())).willReturn(dto);
        //when
        //then
        mockMvc.perform(post("/lectures/apply")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isMap());

    }

    @Test
    @DisplayName("강의 시간과 목록을 조회할 수 있다.")
    void getLectures() throws Exception {
        //given
        //when
        //then
        Lecture lecture1 = new Lecture(1L, "알고리즘 강의", "카카오 개발자가 알려주는 명확한 알고리즘", 2, 0,List.of());
        Lecture lecture2 = new Lecture(2L, "자바 프로그래밍 강의", "자바 초보자를 위한 입문 강의", 3, 1,List.of());

        when(lectureService.getLectures()).thenReturn(Arrays.asList(lecture1, lecture2));

        mockMvc.perform(get("/lectures"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

}