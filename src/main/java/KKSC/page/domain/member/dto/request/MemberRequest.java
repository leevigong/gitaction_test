package KKSC.page.domain.member.dto.request;

import KKSC.page.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MemberRequest(

        @NotNull
        @Email
        String email,

        @NotNull
        @Size(min = 8, max=50, message = "비밀번호 8자리 이상, 50자리 이하로 입력해주세요.")
        String password,

        @NotNull
        @Size(max = 15, message = "이름은 최대 15자까지 가능합니다.")
        String username,

        @NotNull
        @Size(max = 20, message = "학번은 최대 20자까지 가능합니다.")
        String studentId,

        @Size(max = 200, message = "자기소개는 최대 200자까지 가능합니다.")
        String intro,

        @Size(min = 2, max = 10, message = "별명은 최대 10자까지 가능합니다.")
        String nickname
) {
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .username(username)
                .studentId(studentId)
                .build();
    }
}