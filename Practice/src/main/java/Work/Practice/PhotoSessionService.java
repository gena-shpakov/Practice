package Work.Practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
}
