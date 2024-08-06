package KKSC.page.domain.notice.service;

import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface NoticeFileService {

	/**
	 * 공지사항 파일 업로드 
	 * @param noticeBoardId : 파일업로드 하고자 하는 공지사항 게시물의 번호
	 * @return 미정
	 * @since 2024.07.06
	 */
	@PreAuthorize("hasRole('permission_level0 ')")
	String uploadFile(MultipartHttpServletRequest uploadRequestFile, Long noticeBoardId) throws Exception;

	/**
	 * 공지사항 파일 다운로드
	 * @param noticeFileId : 다운로드 하고자 하는 파일의 번호
	 * @return 미정
	 * @since 2024.07.06
	 */
	@PreAuthorize("hasRole('permission_level0 ')")
	Resource downloadFile(Long noticeFileId);

	/**
	 * 공지사항 파일 삭제
	 * @param noticeFileId : 삭제 하고자 하는 파일의 번호
	 * @return 미정
	 * @since 2024.07.06
	 */
	@PreAuthorize("hasRole('permission_level0 ')")
	String deleteFile(Long noticeFileId);
}

