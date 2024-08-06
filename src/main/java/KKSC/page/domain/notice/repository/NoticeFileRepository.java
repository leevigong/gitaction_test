package KKSC.page.domain.notice.repository;

import KKSC.page.domain.notice.dto.NoticeFileResponse;
import KKSC.page.domain.notice.entity.NoticeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeFileRepository extends JpaRepository<NoticeFile, Long> {

    @Query("select new KKSC.page.domain.notice.dto.NoticeFileResponse(" +
            "nf.id, " +
            "nf.noticeFileSize, " +
            "nf.noticeFileName, " +
            "nf.noticeFileType, " +
            "nf.noticeFileDownloadCnt) from NoticeFile nf")
    List<NoticeFileResponse> findNoticeFilesByNoticeBoardId(@Param("id") Long id);
}