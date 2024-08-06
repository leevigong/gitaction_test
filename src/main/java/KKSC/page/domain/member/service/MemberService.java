package KKSC.page.domain.member.service;

import KKSC.page.domain.member.dto.request.MemberLoginRequest;
import KKSC.page.domain.member.dto.request.MemberRequest;
import KKSC.page.domain.member.dto.response.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

public interface MemberService {

    //회원가입
    /**
     * 회원가입
     * @return 미정
     * @since 2024.08.01
     */
    Long register(MemberRequest memberRequest);

    //로그인
    /**
     * 로그인
     * @return 미정
     * @since 2024.08.01
     */
    String login(MemberLoginRequest memberLoginRequest, HttpServletResponse response);

    //회원탈퇴
    /**
     * 회원탈퇴
     * @param email : 탈퇴할 회원의 이메일주소
     * @return 미정
     * @since 2024.08.01
     */
    void retire(String email);

    //프로필 수정 (비밀번호 수정, 권한 관리, 프로필 수정)
    /**
     * 프로필 수정
     * @return 미정
     * @since 2024.08.01
     */
    void update(MemberRequest memberRequest);

    //프로필 조회
    /**
     * 프로필 조회
     * @param email : 프로필 조회할 회원의 이메일
     * @return 미정
     * @since 2024.08.01
     */
    MemberResponse getMemberProfile(String email);

    //로그아웃
    void logout(HttpServletRequest request);
}
