package Work.Practice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    @GetMapping
    public Map<String, String> getSchedule() {
        return Map.of(
                "Monday", "9:00 AM - 5:00 PM",
                "Tuesday", "9:00 AM - 5:00 PM",
                "Wednesday", "9:00 AM - 5:00 PM",
                "Thursday", "9:00 AM - 5:00 PM",
                "Friday", "9:00 AM - 5:00 PM"
        );
    }
}

