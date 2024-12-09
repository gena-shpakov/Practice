package Work.Practice;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class PhotoSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelName;

    private int sessionNumber;

    private LocalDate sessionDate;

    private String photographer;

    private String sessionType;

    public PhotoSession() {}

    public PhotoSession(String modelName, int sessionNumber, LocalDate sessionDate, String photographer, String sessionType) {
        this.modelName = modelName;
        this.sessionNumber = sessionNumber;
        this.sessionDate = sessionDate;
        this.photographer = photographer;
        this.sessionType = sessionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(int sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }
}
