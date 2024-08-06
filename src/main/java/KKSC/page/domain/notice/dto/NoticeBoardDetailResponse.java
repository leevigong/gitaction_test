package KKSC.page.domain.notice.dto;

import KKSC.page.domain.notice.entity.NoticeBoard;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record NoticeBoardDetailResponse(
        String title,
        String content,
        String createdBy,
        Long view,
        Long delYN,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime modifiedAt,
        List<NoticeFileResponse> noticeFileResponses
) {
    public static NoticeBoardDetailResponse from(NoticeBoard noticeBoard, List<NoticeFileResponse> noticeFileResponses) {
        return new NoticeBoardDetailResponse(
                noticeBoard.getTitle(),
                noticeBoard.getContent(),
                noticeBoard.getMemberName(),
                noticeBoard.getView(),
                noticeBoard.getDelYN(),
                noticeBoard.getCreatedAt(),
                noticeBoard.getModifiedAt(),
                noticeFileResponses
        );
    }
}