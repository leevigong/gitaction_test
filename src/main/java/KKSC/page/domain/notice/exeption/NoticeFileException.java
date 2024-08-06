package KKSC.page.domain.notice.exeption;

import KKSC.page.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoticeFileException extends RuntimeException {

    private final ErrorCode errorCode;
}
