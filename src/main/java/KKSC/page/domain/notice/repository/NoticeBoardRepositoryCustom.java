package KKSC.page.domain.notice.repository;

import KKSC.page.domain.notice.dto.NoticeBoardListResponse;
import KKSC.page.domain.notice.entity.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeBoardRepositoryCustom {

    // 키워드 검색
    Page<NoticeBoardListResponse> loadNoticeBoardListByKeyword(Keyword keyword, String query, Pageable pageable);

    Page<NoticeBoardListResponse> loadNoticeBoardList(Pageable pageable);
}
