package KKSC.page.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record NoticeBoardListResponse (
        String title,
        String createdBy,
        Long fileYN,
        Long view,
        Long delYN,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {}