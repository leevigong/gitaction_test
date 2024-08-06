package KKSC.page.domain.notice.entity;

import KKSC.page.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeFile extends BaseTimeEntity {

    // 파일명 고유번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_board_id")
    private NoticeBoard noticeBoard;

    private String noticeFileNameUuid;

    // 사용자가 보는 파일명
    private String noticeFileName;

    // 서버에 해당 파일 주소
    private String noticeFileBaseUrl;

    // 해당 파일 용량
    private Long noticeFileSize;

    // 해당 파일 타입
    private String noticeFileType;

    // 다운로드 횟수 default 0
    @Column(columnDefinition = "integer default 0")
    private int noticeFileDownloadCnt;

    public void addNoticeBoard(NoticeBoard noticeBoard) {
        this.noticeBoard = noticeBoard;
        noticeBoard.getNoticeFiles().add(this);
    }
}