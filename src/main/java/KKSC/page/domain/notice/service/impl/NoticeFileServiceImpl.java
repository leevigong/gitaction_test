package KKSC.page.domain.notice.service.impl;

import KKSC.page.domain.notice.entity.NoticeBoard;
import KKSC.page.domain.notice.entity.NoticeFile;
import KKSC.page.domain.notice.exeption.NoticeBoardException;
import KKSC.page.domain.notice.exeption.NoticeFileException;
import KKSC.page.domain.notice.repository.NoticeBoardRepository;
import KKSC.page.domain.notice.repository.NoticeFileRepository;
import KKSC.page.domain.notice.service.NoticeFileService;
import KKSC.page.global.common.FileCategory;
import KKSC.page.global.common.FileUploadResponse;
import KKSC.page.global.common.FileUtils;
import KKSC.page.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeFileServiceImpl implements NoticeFileService {

    private final NoticeFileRepository noticeFileRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final FileUtils fileUtils;

    // 업로드 경로 지정
    @Value(value = "${fileUploadBaseUrl}")
    private String uploadPath;

    /**
     * 공지사항 파일 업로드
     * @param noticeBoardId : 파일업로드 하고자 하는 공지사항 게시물의 번호
     * @param uploadRequestFile : 업로드하고자 하는 파일
     * @return 업로드완료파일갯수 출력
     * @since 2024.07.06
     * @version 0.01
     * @throws Exception
     * @throws IOException
     */
    @Override
    public String uploadFile(MultipartHttpServletRequest uploadRequestFile, Long noticeBoardId) throws Exception, IOException {
        /*
         * 경로,파일명,파일사이즈,파일타입 빌더패턴으로 묶어서 정보 DB에 저장 후 서버에 파일 업로드
         */
        // 총 업로드 요청한 파일 갯수
        int total = 0;
        // 업로드 된 파일 갯수
        int cnt = 0;

        // 전체 NULL 체크
        if (!ObjectUtils.isEmpty(uploadRequestFile)) {

            Iterator<String> iterator = uploadRequestFile.getFileNames();
            String name;

            while (iterator.hasNext()) {
                total++;
                name = iterator.next();
                
                List<MultipartFile> list = uploadRequestFile.getFiles(name);
                for (MultipartFile multipartFile : list) {

                    if (!(multipartFile.getOriginalFilename() == null
                            || multipartFile.getOriginalFilename().isEmpty())) {

                        // 파일 업로드
                        FileUploadResponse fileUploadResponse = fileUtils.uploadFile(multipartFile,FileCategory.NOTICE_FILE);
                        
                        // DB에 저장
                        NoticeFile noticeFile = NoticeFile.builder()
                                .noticeFileNameUuid(fileUploadResponse.FileNameUuid())
                                .noticeFileBaseUrl(fileUploadResponse.Baseurl())
                                .noticeFileName(multipartFile.getOriginalFilename())
                                .noticeFileType(multipartFile.getContentType())
                                .noticeFileSize(multipartFile.getSize())
                                .build();

                        // 게시글 번호로 게시글 찾아서 파일 추가
                        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeBoardId)
                                .orElseThrow(() -> new NoticeBoardException(ErrorCode.NOT_FOUND_BOARD));

                        noticeFile.addNoticeBoard(noticeBoard);

                        // 파일 여부 업데이트
                        noticeBoard.updateFileYN();

                        noticeFileRepository.save(noticeFile);
                        
                        cnt++;
                    } else // 앞선 파일이 조건에 걸려 안 걸어가면 뒤 파일을 올려야 함
                        continue;
                }
            }
        } else {
            throw new NoticeFileException(ErrorCode.NOT_FOUND_FILE);
        }

        return total + " 중 " + cnt + " 성공 ";
    }

    /**
     * 공지사항 파일 다운로드
     * @param noticeFileId : 다운로드 하고자 하는 파일의 번호
     * @return 파일
     * @since 2024.07.06
     * @version 0.01
     */
    @Override
    public Resource downloadFile(Long noticeFileId) {
        NoticeFile noticeFile = noticeFileRepository.findById(noticeFileId)
                .orElseThrow(() -> new NoticeFileException(ErrorCode.NOT_FOUND_FILE));

       return fileUtils.downloadFile(noticeFile.getNoticeFileBaseUrl(),noticeFile.getNoticeFileName());
    }

    /**
     * 공지사항 파일 삭제
     * @param noticeFileId : 삭제 하고자 하는 파일의 번호
     * @return 파일삭제성공
     * @since 2024.07.06
     * @version 0.01
     */
    @Override
    public String deleteFile(Long noticeFileId) {
        /*
         * 파일아이디로 DB에서 파일 경로 찾아서 삭제 후 DB에서도 해당 데이터 삭제
         */
        NoticeFile noticeFile = noticeFileRepository.findById(noticeFileId)
                .orElseThrow(() -> new NoticeFileException(ErrorCode.NOT_FOUND_FILE));

        NoticeBoard noticeBoard = noticeFile.getNoticeBoard();

        // 파일 삭제
        noticeBoard.deleteFile(noticeFile);
        noticeFileRepository.delete(noticeFile);

        fileUtils.deleteFile(noticeFile.getNoticeFileBaseUrl());

        // 파일 삭제 후 파일 여부 업데이트
        noticeBoard.updateFileYN();

        return "파일 삭제 성공";
    }
}
