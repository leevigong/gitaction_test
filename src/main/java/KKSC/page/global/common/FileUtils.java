package KKSC.page.global.common;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import KKSC.page.domain.notice.exeption.NoticeFileException;
import KKSC.page.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class FileUtils {

    // 업로드 경로 지정
    @Value(value = "${fileUploadBaseUrl}")
    private String uploadPath;

    private final HttpServletResponse response;
    
    /**
     * 파일 업로드
     * @param uploadRequestFile : 파일
     * @param fileCategory : Member / Number / Board 지정
     * @return FileUploadResponse ( 업로드 URL , 업로드 파일명)
     * @since 2024.07.19
     * @version 0.1
     * @throws IOException
     * @throws IllegalStateException
     */
    public FileUploadResponse uploadFile(MultipartFile uploadRequestFile,FileCategory fileCategory) throws IllegalStateException, IOException{
        uploadPath = uploadPath+fileCategory.getCategory();
        File Folder = new File(uploadPath);
        // 폴더없을경우 생성 
        if (!Folder.exists()) {
            Folder.mkdir(); // 디렉터리 생성.
        }
        if (!(uploadRequestFile.getOriginalFilename() == null
            || uploadRequestFile.getOriginalFilename().isEmpty())) {

            // File명 uuid 생성
            String completeUploadPath = uploadPath + "/" + uploadRequestFile.getOriginalFilename() + UUID.randomUUID();
            
            File uploadFile = new File(completeUploadPath);
            
            uploadRequestFile.transferTo(uploadFile);

            return new FileUploadResponse(
                uploadRequestFile.getOriginalFilename() + UUID.randomUUID(), completeUploadPath);
        }
        return null;
    }

    /**
     * 공지사항 파일 다운로드
     * @param downloadBaseUrl : 다운로드 하고자 하는 파일의 주소
     * @param fileName : 파일의 원래 이름
     * @return 파일
     * @since 2024.07.06
     * @version 0.01
     */
    public Resource downloadFile(String downloadBaseUrl,String fileName) {

        try {
            // 파일 경로 지정
            Path filePath = Paths.get(downloadBaseUrl);
            // 파일 불러오기 
            Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기

            // 파일 이름 지정
            String downloadFilename = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            // 헤더 설정
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + downloadFilename + "\"");
            return resource;
        } catch (Exception e) {
            throw new NoticeFileException(ErrorCode.NOT_FOUND_FILE);
        }
    }

    /**
     * 파일 삭제
     * @param deleteFileUrl : 삭제 하고자 하는 파일의 주소
     * @return x
     * @since 2024.07.19
     * @version 0.01
     */
    public void deleteFile(String deleteFileUrl) {
        
        // 파일 지정
        File file = new File(deleteFileUrl);
        // 파일 삭제
        file.delete();
    }
}
