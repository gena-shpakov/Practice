package Work.Practice;

import Work.Practice.dto.PhotoSessionDTO;
import Work.Practice.entity.PhotoSession;
import Work.Practice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/photosessions")
public class PhotoSessionController {

    @Autowired
    private PhotoSessionService service;

    @GetMapping
    public List<PhotoSession> getAllSessions() {
        return service.getAllSessions();
    }

    @GetMapping("/{id}")
    public PhotoSession getSessionById(@PathVariable Long id) {
        return service.getSessionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PhotoSession not found with ID: " + id));
    }

    @PostMapping
    public PhotoSession createSession(@Valid @RequestBody PhotoSessionDTO sessionDTO) {
        PhotoSession session = new PhotoSession();
        session.setEventName(sessionDTO.getEventName());
        session.setStartDate(sessionDTO.getStartDate());
        session.setEndDate(sessionDTO.getEndDate());
        session.setLocation(sessionDTO.getLocation());
        session.setOrganizer(sessionDTO.getOrganizer());
        return service.saveSession(session);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        service.deleteSession(id);
    }

    @GetMapping("/schedule")
    public Map<java.time.DayOfWeek, List<PhotoSession>> getScheduleGroupedByDayOfWeek() {
        return service.getScheduleGroupedByDayOfWeek();
    }
}




