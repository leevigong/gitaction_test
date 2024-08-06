package KKSC.page.domain.notice.entity;

import KKSC.page.domain.notice.dto.NoticeBoardRequest;
import KKSC.page.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of = {"id", "title", "content"})
public class NoticeBoard extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_board_id")
    private Long id;

    private String memberName;

    @OneToMany(mappedBy = "noticeBoard")
    private List<NoticeFile> noticeFiles;

    private Long fileYN; /* 첨부파일 여부 */

    @Enumerated(EnumType.STRING)
    private Keyword keyword; /* (제목, 내용, 작성자)로 검색. default 제목 */

    private String title;
    private String content;

    private Long view; /* 조회수 */
    private Long fixed; /* 상단 고정 여부 */
    private Long delYN; /* 삭제 여부 */

    public void update(NoticeBoardRequest noticeBoardRequest) {
        this.title = noticeBoardRequest.title();
        this.content = noticeBoardRequest.content();
        this.fixed = noticeBoardRequest.fixed();
    }

    public void viewUp() {
        this.view++;
    }

    public void delete() {
        this.delYN = 1L;
    }

    public void deleteFile(NoticeFile noticeFile) {
        for (NoticeFile file : noticeFiles) {
            if (file.getId().equals(noticeFile.getId())) {
                noticeFiles.remove(file);
                break;
            }
        }
    }

    public void updateFileYN() {
        this.fileYN = noticeFiles.isEmpty() ? 0L : 1L;
    }
}
