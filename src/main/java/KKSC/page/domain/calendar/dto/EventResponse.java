package KKSC.page.domain.calendar.dto;

import KKSC.page.domain.calendar.entity.Category;
import KKSC.page.domain.calendar.entity.Event;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        String detail,
        Category category,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDate,
        Long maxParticipant
) {

    public static EventResponse from(Event event) {
        return new EventResponse(event.getId(), event.getTitle(), event.getDetail(),
                event.getCategory(), event.getStartDate(), event.getEndDate(), event.getMaxParticipant());
    }
}
