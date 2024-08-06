package KKSC.page.domain.member.entity;

import KKSC.page.domain.member.dto.request.MemberRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "profile")
    private Member member;

    /**
     * photo 추가 예정
     */

    //자기소개
    private String intro;

    @Column(name = "nickname", length = 50)
    private String nickname;

    public void update(MemberRequest memberRequest) {
        this.intro = memberRequest.intro();
        this.nickname = memberRequest.nickname();
        //포토 추가예정
    }
}
