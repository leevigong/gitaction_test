package KKSC.page.domain.notice.dto;

import KKSC.page.domain.notice.entity.Keyword;
import KKSC.page.domain.notice.entity.NoticeBoard;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;

public record NoticeBoardRequest(
        @NotBlank(message = "제목을 입력하시오.")
        @Size(max = 20, message = "최대 글자수를 초과하였습니다.")
        String title,
        @NotBlank(message = "내용을 입력하시오.")
        String content,
        @Min(0) @Max(1)
        Long fixed
) {

    public NoticeBoard toEntity(String memberName) {
        return NoticeBoard.builder()
                .title(title()).content(content()).fixed(fixed())
                .keyword(Keyword.TITLE).delYN(0L)
                .view(0L).memberName(memberName)
                .noticeFiles(new ArrayList<>())
                .build();
    }
}