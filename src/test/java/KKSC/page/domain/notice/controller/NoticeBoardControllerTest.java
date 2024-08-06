package KKSC.page.domain.notice.controller;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.repository.NoticeBoardRepository;
import KKSC.page.domain.notice.service.NoticeBoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoticeBoardController.class)
@WithMockUser
class NoticeBoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    NoticeBoardService noticeBoardService;

    @MockBean
    NoticeBoardRepository noticeBoardRepository;

//    @Test
//    void 게시글_목록_조회() throws Exception {
//        //given
//        List<NoticeBoardListResponse> mockResponse = List.of(
//                new NoticeBoardListResponse("title1", "writer1", 0L, 0L, 0L, LocalDateTime.now()),
//                new NoticeBoardListResponse("title2", "writer2", 0L, 0L, 0L, LocalDateTime.now())
//        );
//        given(noticeBoardService.getBoardList()).willReturn(mockResponse);
//
//        //when & then
//        mockMvc.perform(get("/notice/list"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.size()").value(mockResponse.size()))
//                .andExpect(jsonPath("$.data[0].title").value(mockResponse.get(0).title()))
//                .andExpect(jsonPath("$.data[1].title").value(mockResponse.get(1).title()));
//    }

    @Test
    void 게시글_단건_조회() throws Exception {
        //given
        NoticeBoardDetailResponse mockResponse = new NoticeBoardDetailResponse("title", "content",
                "writer", 0L, 0L, LocalDateTime.now(), LocalDateTime.now(), null);
        given(noticeBoardService.getBoardDetail(1L)).willReturn(mockResponse);

        //when & then
        mockMvc.perform(get("/notice/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(mockResponse.title()))
                .andExpect(jsonPath("$.data.content").value(mockResponse.content()));
    }

    @Test
    void 게시글_작성_성공() throws Exception {
        //given
        NoticeBoardRequest request = new NoticeBoardRequest("title", "content", 0L);
        given(noticeBoardService.create(request, "admin")).willReturn(0L); // Test 코드의 경우 실제 Service단까지 내려가지 않기 때문에 1L 반환 X

        //when & then
        mockMvc.perform(post("/notice/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(jsonPath("$.data").value(0L))
                .andExpect(status().isOk());
    }

    @Test
    void 게시글_수정() throws Exception {
        //given
        NoticeBoardRequest request = new NoticeBoardRequest("title", "content", 0L); // 기존 게시글
        NoticeBoardRequest updateRequest = new NoticeBoardRequest("title-update", "content-update", 0L); // 수정할 게시글

        given(noticeBoardService.create(request, "admin")).willReturn(0L); // 기존 게시글 생성
        given(noticeBoardService.update(1L, updateRequest)).willReturn(new NoticeBoardDetailResponse("title-update", "content-update",
                "admin", 0L, 0L, LocalDateTime.now(), LocalDateTime.now(), null)); // 수정할 게시글 반환

        //when & then
        mockMvc.perform(put("/notice/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("title-update"))
                .andExpect(jsonPath("$.data.content").value("content-update"));
    }

    @Test
    void 게시글_삭제_성공() throws Exception {
        //when & then
        mockMvc.perform(delete("/notice/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Delete success"));
    }
}