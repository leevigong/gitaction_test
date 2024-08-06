package KKSC.page.domain.notice.exeption;

import KKSC.page.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoticeBoardException extends RuntimeException {

    private final ErrorCode errorCode;
}
