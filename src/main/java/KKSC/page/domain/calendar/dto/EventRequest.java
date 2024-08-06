package KKSC.page.domain.calendar.dto;

import KKSC.page.domain.calendar.entity.Category;
import KKSC.page.domain.calendar.entity.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;

public record EventRequest(
        String title,
        String detail,
        Category category,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long maxParticipant) {

    public Event toEntity() {
        return Event.builder()
                .title(title)
                .detail(detail)
                .category(category)
                .startDate(startDate)
                .endDate(endDate)
                .maxParticipant(maxParticipant)
                .participants(new ArrayList<>())
                .build();
    }
}
