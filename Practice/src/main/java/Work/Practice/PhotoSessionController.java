package Work.Practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class PhotoSessionController {

    @Autowired
    private PhotoSessionService service;

    @GetMapping
    public List<PhotoSession> getAllSessions() {
        return service.getAllSessions();
    }

    @GetMapping("/{id}")
    public PhotoSession getSessionById(@PathVariable Long id) {
        return service.getSessionById(id).orElse(null);
    }

    @PostMapping
    public PhotoSession createSession(@RequestBody PhotoSession session) {
        return service.saveSession(session);
    }

    @PutMapping("/{id}")
    public PhotoSession updateSession(@PathVariable Long id, @RequestBody PhotoSession session) {
        session.setId(id);
        return service.saveSession(session);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        service.deleteSession(id);
    }
}

