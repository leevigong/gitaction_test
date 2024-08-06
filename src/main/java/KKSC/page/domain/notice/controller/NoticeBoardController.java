package KKSC.page.domain.notice.controller;

import KKSC.page.domain.member.exception.MemberException;
import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.entity.Keyword;
import KKSC.page.domain.notice.service.NoticeBoardService;
import KKSC.page.global.auth.service.JwtService;
import KKSC.page.global.exception.ErrorCode;
import KKSC.page.global.exception.dto.ResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;
    private final JwtService jwtService;

    // 게시글 작성
    @Operation(summary = " 게시물 작성 ", description = " 게시물 작성 ")
    @PostMapping("/")
    public ResponseVO<Long> noticeCreate(@RequestBody @Valid NoticeBoardRequest request) {
        Long createdId = noticeBoardService.create(request, null);// memberName은 이후 로그인 구현 후 추가

        return new ResponseVO<>(createdId);
    }

    // 게시글 수정
    @Operation(summary = " 게시물 수정 ", description = " 게시물 수정 ")
    @PutMapping("/{id}")
    public ResponseVO<NoticeBoardDetailResponse> noticeUpdate(@PathVariable("id") Long id, @RequestBody @Valid NoticeBoardRequest request) {
        NoticeBoardDetailResponse detailResponse = noticeBoardService.update(id, request);

        return new ResponseVO<>(detailResponse);
    }

    // 게시글 삭제
    @Operation(summary = " 게시물 삭제 ", description = " 게시물 삭제 ")
    @DeleteMapping("/{id}")
    public ResponseVO<String> noticeDelete(@PathVariable("id") Long id) {
        noticeBoardService.delete(id);

        return new ResponseVO<>("Delete success");
    }

    // 게시글 목록 조회(페이징)
    @Operation(summary = " 게시글 목록 조회(페이징) ", description = " 게시물 목록 조회(페이징) ")
    @GetMapping("/list")
    public ResponseVO<Page<NoticeBoardListResponse>> noticeListPage(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NoticeBoardListResponse> listResponses = noticeBoardService.getBoardList(pageable);
        return new ResponseVO<>(listResponses);
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    @Operation(summary = " 게시물 조회 ", description = " 게시물 조회 ")
    public ResponseVO<NoticeBoardDetailResponse> noticeDetail(@PathVariable("id") Long id,
                                                              HttpServletRequest request, HttpServletResponse response) {
        // 현재 로그인한 사용자 정보 가져오기
        String username = jwtService.extractUsername(request)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_ACCESS_TOKEN));

        String newUsername = username.replaceAll("[@.]", "_");
        String cookieName = "noticeBoardView_" + newUsername;

        // 쿠키 값 가져오기
        String noticeBoardViewCookie = getCookieValue(request, cookieName);

        // 조회수 증가 + 쿠키 값 추가
        noticeBoardService.readNotice(id, cookieName, noticeBoardViewCookie, response);

        // 글 조회
        NoticeBoardDetailResponse detailResponse = noticeBoardService.getBoardDetail(id);
        return new ResponseVO<>(detailResponse);
    }

    // 특정 키워드로 검색
    @Operation(summary = " 게시물 특정 키워드로 검색 ", description = " 게시물 특정 키워드로 검색 ")
    @GetMapping("/search")
    public ResponseVO<Page<NoticeBoardListResponse>> searchBoards(
            @RequestParam(defaultValue = "TITLE") Keyword keyword,
            @RequestParam String query,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NoticeBoardListResponse> listResponses = noticeBoardService.searchBoardList(keyword, query, pageable);

        return new ResponseVO<>(listResponses);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }
}