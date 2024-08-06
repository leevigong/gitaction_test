package KKSC.page.global.exception;

import KKSC.page.domain.member.exception.MemberException;
import KKSC.page.domain.notice.exeption.NoticeBoardException;
import KKSC.page.domain.notice.exeption.NoticeFileException;
import KKSC.page.global.exception.dto.ErrorResponseVO;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoticeBoardException.class)
    public ErrorResponseVO handleNoticeBoardException(NoticeBoardException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return getErrorResponse(errorCode);
    }

    @ExceptionHandler(NoticeFileException.class)
    public ErrorResponseVO handleNoticeFileException(NoticeFileException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return getErrorResponse(errorCode);
    }

    @ExceptionHandler(MemberException.class)
    public ErrorResponseVO handleMemberException(MemberException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return getErrorResponse(errorCode);
    }

    @ResponseStatus(org.springframework.http.HttpStatus.FORBIDDEN)
    @ExceptionHandler(TokenExpiredException.class)
    public ErrorResponseVO handleTokenExpiredException(TokenExpiredException ex) {
        return ErrorResponseVO.builder()
                .name(ErrorCode.EXPIRED_ACCESS_TOKEN.name())
                .errorCode(ErrorCode.EXPIRED_ACCESS_TOKEN.getErrorCode())
                .message(ErrorCode.EXPIRED_ACCESS_TOKEN.getMessage()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseVO handleValidationException(MethodArgumentNotValidException ex) {
        return ErrorResponseVO.builder()
                .name("VALIDATION_ERROR")
                .errorCode(ex.getStatusCode().value())
                .message(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage()).build();
    }

    private ErrorResponseVO getErrorResponse(ErrorCode errorCode) {
        return ErrorResponseVO.builder()
                .name(errorCode.name())
                .errorCode(errorCode.getErrorCode())
                .message(errorCode.getMessage()).build();
    }
}
