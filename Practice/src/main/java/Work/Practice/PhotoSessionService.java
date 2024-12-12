package Work.Practice;

import Work.Practice.entity.PhotoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotoSessionService {

    @Autowired
    private PhotoSessionRepository repository;

    public List<PhotoSession> getAllSessions() {
        return repository.findAll();
    }

    public Optional<PhotoSession> getSessionById(Long id) {
        return repository.findById(id);
    }

    public PhotoSession saveSession(PhotoSession session) {
        return repository.save(session);
    }

    public void deleteSession(Long id) {
        repository.deleteById(id);
    }

    public Map<DayOfWeek, List<PhotoSession>> getScheduleGroupedByDayOfWeek() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(session -> session.getStartDate().getDayOfWeek()));
    }
}




