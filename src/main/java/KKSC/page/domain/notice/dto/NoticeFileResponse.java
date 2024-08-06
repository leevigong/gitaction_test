package KKSC.page.domain.notice.dto;

public record NoticeFileResponse(
        Long fileId,
        Long fileSize,
        String fileName,
        String fileType,
        int downloadCnt
) {}
