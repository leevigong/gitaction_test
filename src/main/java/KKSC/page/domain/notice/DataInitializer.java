package KKSC.page.domain.notice;

import KKSC.page.domain.member.dto.request.MemberRequest;
import KKSC.page.domain.member.service.MemberService;
import KKSC.page.domain.notice.entity.NoticeBoard;
import KKSC.page.domain.notice.repository.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final NoticeBoardRepository noticeBoardRepository;
    private final MemberService memberService;

    @Override
    public void run(String... args) throws Exception {
        // 100개의 게시글 생성
        for (int i = 0; i < 100; ++i) {
            NoticeBoard noticeBoard = NoticeBoard.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .memberName("member" + i)
                    .delYN(0L)
                    .view(0L)
                    .build();
            noticeBoardRepository.save(noticeBoard);
        }

        // 사용자 계정 생성
        MemberRequest memberRequest = new MemberRequest(
                "kksc@mail.com", "12345678", "kksc", "20241234", null, null
        );

        memberService.register(memberRequest);
    }
}
