package KKSC.page.global.auth.service;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.member.repository.MemberRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class JwtServiceTest {

    @Autowired JwtService jwtService;
    @Autowired MemberRepository memberRepository;

    @Value("${jwt.secret}")
    String secret;
    @Value("${jwt.access.header}")
    String accessHeader;
    @Value("${jwt.refresh.header}")
    String refreshHeader;

    static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    static final String USERNAME_CLAIM = "email";
    static final String BEARER = "Bearer ";
    String email = "ww@mail";

    @BeforeEach
    void setUp() {
        Member member = Member.builder().email(email).password("1234").build();

        memberRepository.saveAndFlush(member);
    }

    DecodedJWT getVerify(String token) {
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
    }

    @Test
    void accessToken_발급() throws Exception {
        //given
        String accessToken = jwtService.createAccessToken(email);

        //when
        DecodedJWT verify = getVerify(accessToken);
        String subject = verify.getSubject();
        String claim = verify.getClaim(USERNAME_CLAIM).asString();

        //then
        assertThat(subject).isEqualTo(ACCESS_TOKEN_SUBJECT);
        assertThat(claim).isEqualTo(email);
    }
}