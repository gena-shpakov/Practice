package Work.Practice;

import Work.Practice.entity.PhotoSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoSessionRepository extends JpaRepository<PhotoSession, Long> {
}
