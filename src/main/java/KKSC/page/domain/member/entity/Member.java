package KKSC.page.domain.member.entity;

import KKSC.page.domain.calendar.entity.Event;
import KKSC.page.domain.member.dto.request.MemberRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString(of = {"id", "email", "password"})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Event> events;

    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    //@Column(name = "password", nullable = false)
    private String password;

    //    @Column(name = "username", nullable = false, unique = true)
    private String username;

    //    @Column(name = "email", nullable = false, unique = true)
    private String email;

    //    @Column(name = "student_id", nullable = false, unique = true)
    private String studentId;

    @Enumerated(EnumType.STRING)
    //    @Column(name = "permission", nullable = false)
    private Permission permission;

    private String refreshToken;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updatePassword(MemberRequest memberRequest) {
        this.password = memberRequest.password();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void destroyRefreshToken() {
        this.refreshToken = null;
    }
}