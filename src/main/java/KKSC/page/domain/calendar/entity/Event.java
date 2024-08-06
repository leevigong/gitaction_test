package KKSC.page.domain.calendar.entity;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    // 참가자 리스트
    @OneToMany(mappedBy = "event")
    private List<Participant> participants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 일정 제목
    private String title;

    // 일정 카테고리(분류)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    // 참가 인원 추가
    private Long maxParticipant;

    // 일정 시작 날짜
    private LocalDateTime startDate;

    // 일정 종료 날짜
    private LocalDateTime endDate;

    // 일정 세부사항
    private String detail;

    // 일정 수정(시작 날짜, 종료 날짜, 세부 사항 수정)
    public void update(String title, String detail, Category category, LocalDateTime startDate, LocalDateTime endDate, Long maxParticipant) {
        this.title = title;
        this.detail = detail;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxParticipant = maxParticipant;
    }
}
