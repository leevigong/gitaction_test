package KKSC.page.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileCategory {

    NOTICE_FILE("Notice_file"),
    MEMBER("Member_file"),
    BOARD("Board_file");

    private final String Category;
}
