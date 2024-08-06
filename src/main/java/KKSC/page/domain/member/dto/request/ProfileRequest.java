package KKSC.page.domain.member.dto.request;

import jakarta.validation.constraints.Size;

public record ProfileRequest(

        @Size(max = 200, message = "자기소개는 최대 200자까지 가능합니다.")
        String intro,

        @Size(min = 2, max = 10, message = "별명은 최대 10자까지 가능합니다.")
        String nickname
        //포토 추가예정
) {}