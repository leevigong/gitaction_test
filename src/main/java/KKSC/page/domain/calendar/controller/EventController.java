package KKSC.page.domain.calendar.controller;

import KKSC.page.domain.calendar.dto.EventRequest;
import KKSC.page.domain.calendar.repoAndService.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class EventController {

    private final EventService eventService;

    // 캘린더 일정 추가
    @PostMapping("/")
    public void addEvent(@RequestBody EventRequest eventRequest) {
        eventService.createSchedule(eventRequest);
    }

    // 캘린더 일정 삭제
    @DeleteMapping("/{id}")
    public void deleteEvent(@RequestParam Long id) {
        eventService.deleteSchedule(id);
    }

    // 캘린더 일정 수정
    @PutMapping("/{id}")
    public void updateEvent(@RequestParam Long id, @RequestBody EventRequest eventRequest) {
        eventService.updateSchedule(id, eventRequest);
    }

    // 캘린더 일정 참가
    @PostMapping("/{id}/join")
    public void joinEvent(@RequestParam Long id) {
        eventService.joinSchedule(id, null); // name 수정 필요
    }

    // 캘린더 일정 참가 취소
    @DeleteMapping("/cancel/{id}")
    public void cancelEvent(@RequestParam Long id) {
        eventService.cancelSchedule(id);
    }

    // 캘린더 일정 목록 조회
    @GetMapping("/")
    public void getEventList(@RequestParam Long year, @RequestParam Long month) {
        eventService.getScheduleList(year, month);
    }
}
