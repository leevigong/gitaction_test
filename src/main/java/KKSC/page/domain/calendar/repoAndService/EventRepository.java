package KKSC.page.domain.calendar.repoAndService;

import KKSC.page.domain.calendar.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {
}
