package KKSC.page.domain.notice.controller;

import KKSC.page.global.exception.dto.ErrorResponseVO;
import KKSC.page.global.exception.dto.ResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import KKSC.page.domain.notice.service.NoticeFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Notice_File", description = "공지사항 파일관련 API")
@RestController
@RequestMapping("/noticefile")
@RequiredArgsConstructor
@Slf4j
public class NoticeFileController {

    private final NoticeFileService noticeFileService;

    // 공지사항 첨부파일 업로드
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API 정상 작동",content = @Content(schema = @Schema(implementation = ResponseVO.class))),
            @ApiResponse(responseCode = "???", description = "서버 에러",content = @Content(schema = @Schema(implementation = ErrorResponseVO.class)))}
    )
    @Parameters({
        @Parameter(name = "noticeboardid", description = "공지사항 번호"),
        @Parameter(name = "uploadRequestFile", description = "업로드 파일"),
    })
    @Operation(summary = " 공지사항 파일 업로드 ", description = "공지사항 파일 업로드 ")
    @PostMapping("/{noticeboardid}")
    public ResponseVO<String> NoticeFileupload(MultipartHttpServletRequest uploadRequestFile,
                                               @PathVariable Long noticeboardid) throws Exception {

        return new ResponseVO<>(noticeFileService.uploadFile(uploadRequestFile, noticeboardid));
    }

    // 공지사항 첨부파일 다운로드
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "API 정상 작동",content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
        @ApiResponse(responseCode = "???", description = "서버 에러",content = @Content(schema = @Schema(implementation = ErrorResponseVO.class)))}
    )
    @Parameter(name = "noticeFileId", description = "공지사항 파일 번호")
    @Operation(summary = " 공지사항 파일 다운로드 ", description = "공지사항 파일 다운로드 ")
    @GetMapping("/{noticeFileId}")
    public ResponseEntity<Resource> NoticeFileDownload(@PathVariable Long noticeFileId) throws Exception {

        return ResponseEntity.ok().body(noticeFileService.downloadFile(noticeFileId));
    }

    // 공지사항 첨부파일 삭제
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "API 정상 작동",content = @Content(schema = @Schema(implementation = ResponseVO.class))),
        @ApiResponse(responseCode = "???", description = "서버 에러",content = @Content(schema = @Schema(implementation = ErrorResponseVO.class)))}
    )
    @Parameter(name = "noticeFileId", description = "공지사항 파일 번호")
    @Operation(summary = " 공지사항 파일 삭제 ", description = "공지사항 파일 삭제 ")
    @DeleteMapping("/{noticeFileId}")
    public ResponseVO<String>  NoticeFileDelete(@PathVariable Long noticeFileId) throws Exception {

        return new ResponseVO<>(noticeFileService.deleteFile(noticeFileId));
    }


}
