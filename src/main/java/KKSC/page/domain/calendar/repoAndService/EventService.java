package KKSC.page.domain.calendar.repoAndService;

import org.springframework.security.access.prepost.PreAuthorize;

import KKSC.page.domain.calendar.dto.EventRequest;
import KKSC.page.domain.calendar.dto.EventResponse;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface EventService {
    
    // 일정 목록 조회
    @PreAuthorize("hasRole('permission_level1 ')")
    List<EventResponse> getScheduleList(Long year, Long month);

    // 일정 생성
    @PreAuthorize("hasRole('permission_level0 ')")
    Long createSchedule(EventRequest eventRequest);

    // 일정 삭제
    @PreAuthorize("hasRole('permission_level0 ')")
    void deleteSchedule(Long id);

    // 일정 수정
    @PreAuthorize("hasRole('permission_level0 ')")
    EventResponse updateSchedule(Long id, EventRequest eventRequest);

    // 일정 참가
    @PreAuthorize("hasRole('permission_level1 ')")
    Long joinSchedule(Long id, String name);

    // 일정 참가 취소
    @PreAuthorize("hasRole('permission_level1 ')")
    void cancelSchedule (Long id);

}
