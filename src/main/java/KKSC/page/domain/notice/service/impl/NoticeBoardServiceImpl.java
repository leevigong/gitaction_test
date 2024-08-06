package KKSC.page.domain.notice.service.impl;

import KKSC.page.domain.notice.dto.NoticeBoardDetailResponse;
import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.domain.notice.dto.NoticeFileResponse;
import KKSC.page.domain.notice.entity.Keyword;
import KKSC.page.domain.notice.entity.NoticeBoard;
import KKSC.page.domain.notice.exeption.NoticeBoardException;
import KKSC.page.domain.notice.repository.NoticeBoardRepository;
import KKSC.page.domain.notice.repository.NoticeFileRepository;
import KKSC.page.domain.notice.service.NoticeBoardService;
import KKSC.page.global.exception.ErrorCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final NoticeFileRepository noticeFileRepository;

    /*
     * noticeBoardRequest 를 통해 noticeBoard 객체 생성
     * noticeBoardRepository 에 저장
     */
    @Override
    public Long create(NoticeBoardRequest noticeBoardRequest, String memberName) {
        NoticeBoard noticeBoard = noticeBoardRequest.toEntity(memberName);
        return noticeBoardRepository.save(noticeBoard).getId();
    }

    /*
     * noticeBoardRepository에서 noticeBoardId로 기존 수정할 게시글 가져오기
     * noticeBoardRequest 내용으로 수정
     * 성공적으로 수정되었다면 NoticeBoardDetailResponse에 수정한 내용 담아서 반환
     */
    @Override
    public NoticeBoardDetailResponse update(Long noticeBoardId, NoticeBoardRequest noticeBoardRequest) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new NoticeBoardException(ErrorCode.NOT_FOUND_BOARD));

        List<NoticeFileResponse> noticeFileResponses = noticeFileRepository.findNoticeFilesByNoticeBoardId(noticeBoardId);
        noticeBoard.update(noticeBoardRequest);

        return NoticeBoardDetailResponse.from(noticeBoard, noticeFileResponses);
    }

    /*
     * 삭제할 noticeBoardId를 repository 에서 찾아옴
     * del_YN: 0 -> 1
     */
    @Override
    public void delete(Long noticeBoardId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new NoticeBoardException(ErrorCode.NOT_FOUND_BOARD));

        if (noticeBoard.getDelYN() == 1L) {
            throw new NoticeBoardException(ErrorCode.ALREADY_DELETED);
        }
        noticeBoard.delete();
    }

    @Override
    public Page<NoticeBoardListResponse> getBoardList(Pageable pageable) {
        return noticeBoardRepository.loadNoticeBoardList(pageable);
    }

    /*
     * noticeBoardId의 글을 repo에서 가져와서 response에 담아서 반환
     * 성공적으로 조회되었다면 200 OK
     */
    @Override
    public NoticeBoardDetailResponse getBoardDetail(Long noticeBoardId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new NoticeBoardException(ErrorCode.NOT_FOUND_BOARD));

        if (noticeBoard.getDelYN() == 1L) {
            throw new NoticeBoardException(ErrorCode.ALREADY_DELETED);
        }

        List<NoticeFileResponse> noticeFileResponses = noticeFileRepository.findNoticeFilesByNoticeBoardId(noticeBoardId);

        return NoticeBoardDetailResponse.from(noticeBoard, noticeFileResponses);
    }

    /*
     * String, keyword: null 이면 title
     * keyword type에 따라 어디서 %like% 쓸지
     */
    @Override
    public Page<NoticeBoardListResponse> searchBoardList(Keyword keyword, String query, Pageable pageable) {
        return noticeBoardRepository.loadNoticeBoardListByKeyword(keyword, query, pageable);
    }

    @Override
    public void countUpView(Long noticeBoardId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
                .orElseThrow(() -> new NoticeBoardException(ErrorCode.NOT_FOUND_BOARD));
        noticeBoard.viewUp();

        noticeBoardRepository.save(noticeBoard);
    }

    @Override
    public void readNotice(Long noticeBoardId, String cookieName, String cookieValue, HttpServletResponse response) {
        String noticeBoardValue = "[" + noticeBoardId + "]";

        if (cookieValue == null) { // cookieValue 없으면 새로운 쿠키
            countUpView(noticeBoardId); // 조회수 +1
            Cookie newCookie = new Cookie(cookieName, noticeBoardValue);
            newCookie.setPath("/notice");
            newCookie.setMaxAge(60 * 60 * 24); // 수명: 24시간
            response.addCookie(newCookie);
        } else { // cookieValue 있으면 기존 쿠키 -> 기존 쿠키값에 조회한 게시글 id 추가
            if (!cookieValue.contains(noticeBoardValue)) {
                countUpView(noticeBoardId); // 조회수 +1
                String newValue = cookieValue + noticeBoardValue;
                Cookie oldCookie = new Cookie(cookieName, newValue);
                oldCookie.setPath("/notice");
                oldCookie.setMaxAge(60 * 60 * 24); // 수명: 24시간
                response.addCookie(oldCookie);
            }
        }
    }
}
