package KKSC.page.domain.member.dto.request;

import KKSC.page.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MemberLoginRequest(

        @NotNull
        @Email
        String email,

        @NotNull
        @Size(min = 8, max=50, message = "비밀번호 8자리 이상, 50자리 이하로 입력해주세요.")
        String password
) {}