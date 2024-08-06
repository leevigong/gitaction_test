package KKSC.page.domain.member.service.impl;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.member.entity.MemberDetails;
import KKSC.page.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(username + "-> 사용자를 찾을 수 없습니다.")
        );

        log.info("DB에서 가져온 유저정보 username = {}, password = {}", member.getEmail(), member.getPassword());
        return new MemberDetails(member);
    }


}
