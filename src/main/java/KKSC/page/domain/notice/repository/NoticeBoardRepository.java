package KKSC.page.domain.notice.repository;

import KKSC.page.domain.notice.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long>, NoticeBoardRepositoryCustom {
}
