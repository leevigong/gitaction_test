package KKSC.page.domain.notice.service;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.entity.Keyword;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface NoticeBoardService {

    /**
     * 공지사항 게시물 작성
     * @param memberName : 게시물 작성자 이름
     * @return 미정
     * @since 2024.08.01
     */
    @PreAuthorize("hasRole('permission_level0')")
    Long create(NoticeBoardRequest noticeBoardRequest, String memberName);

    /**
     * 공지사항 게시물 수정
     * @param noticeBoardId : 수정할 게시물의 번호
     * @return 미정
     * @since 2024.08.01
     */
    @PreAuthorize("hasRole('permission_level0')")
    NoticeBoardDetailResponse update(Long noticeBoardId, NoticeBoardRequest noticeBoardRequest);

    /**
     * 공지사항 게시물 삭제
     * @param noticeBoardId : 삭제할 게시물의 번호
     * @return 미정
     * @since 2024.08.01
     */
    @PreAuthorize("hasRole('permission_level0')")
    void delete(Long noticeBoardId);

    /**
     * 공지사항 게시물 목록 조회
     * @return 미정
     * @since 2024.08.01
     */
    @PreAuthorize("hasRole('permission_level1')")
    Page<NoticeBoardListResponse> getBoardList(Pageable pageable); /* 기본적으로 보여줄 게시글 목록 */

    /**
     * 공지사항 게시물 상세조회
     * @param noticeBoardId : 상세조회할 게시물의 번호
     * @return 미정
     * @since 2024.08.01
     */
    @PreAuthorize("hasRole('permission_level1')")
    NoticeBoardDetailResponse getBoardDetail(Long noticeBoardId); /* 글 선택 시 보여줄 상세 글 */

    /**
     * 공지사항 게시물 검색
     * @param keyword : 검색키워드
     * @return 미정
     * @since 2024.08.01
     */
    @PreAuthorize("hasRole('permission_level1')")
    Page<NoticeBoardListResponse> searchBoardList(Keyword keyword, String query, Pageable pageable); /* 작성일 순, 작성자가 포함된 글 불러오기 */

    void countUpView(Long noticeBoardId);

    void readNotice(Long noticeBoardId, String cookieName, String cookieValue, HttpServletResponse response);
}
