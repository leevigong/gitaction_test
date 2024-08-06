package KKSC.page.domain.notice.repository;

import KKSC.page.domain.member.repository.MemberRepository;
import KKSC.page.domain.notice.entity.Keyword;
import KKSC.page.domain.notice.entity.NoticeBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class NoticeBoardRepositoryTest {

    @Autowired NoticeBoardRepository noticeBoardRepository;
    @Autowired NoticeFileRepository noticeFileRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    void findAll_조회() throws Exception {
        //given
        NoticeBoard board = NoticeBoard.builder()
                .title("title").content("content").keyword(Keyword.TITLE)
                .fixed(0L).delYN(0L).view(0L).build();

        //when
        noticeBoardRepository.save(board);

        //then
        List<NoticeBoard> boards = noticeBoardRepository.findAll();
        boards.forEach(System.out::println);
        assertThat(1).isEqualTo(boards.size());
    }
}