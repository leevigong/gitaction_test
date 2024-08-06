package KKSC.page.domain.calendar.repoAndService.impl;

import KKSC.page.domain.calendar.dto.EventRequest;
import KKSC.page.domain.calendar.dto.EventResponse;
import KKSC.page.domain.calendar.entity.Event;
import KKSC.page.domain.calendar.repoAndService.EventRepository;
import KKSC.page.domain.calendar.repoAndService.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    
    private final EventRepository eventRepository;

    // 일정 목록 조회    
    // 파라미터 : 몇년 몇월 받아오기
    @Override
    public List<EventResponse> getScheduleList(Long year, Long month) {
        // getScheduleList 메소드 (Repo Impl)에서 구현하기 (한달치 긁어오기)

        eventRepository.getScheduleList();
        return null;
    };
    
    // 일정 생성
    // 파라미터 : 스케쥴 생성 값 
    @Override
    public Long createSchedule(EventRequest eventRequest) {
        Event event = eventRequest.toEntity();

        // event에서 member 서로 추가해야 하는데 다대다 매핑 필요

        eventRepository.save(event);
        return null;
    };
    
    // 일정 삭제
    // 파라미터 : 스케줄 아이디
    @Override
    public void deleteSchedule(Long id) {
        eventRepository.deleteAllById(null);
    };
    
    // 일정 수정
    // 파라미터 : 스케줄 수정 값
    @Override
    public EventResponse updateSchedule(Long id, EventRequest eventRequest) {
        eventRepository.save(null);
        return null;
    };
    
    // 일정 참가
    // 파라미터 : 스케줄 아이디, 유저아이디
    @Override
    public Long joinSchedule(Long id, String name) {
        eventRepository.save(null);
        return null;
    };
    
    // 일정 참가 취소
    // 파라미터 : 스케줄 아이디, 유저아이디
    @Override
    public void cancelSchedule (Long id) {
        eventRepository.delete(null);
    };
}
