package Work.Practice;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/entity")
public class EntityController {

    @GetMapping
    public ResponseEntity<String> getAllEntities() {
        return ResponseEntity.ok("All entities fetched");
    }

    @PostMapping
    public ResponseEntity<String> createEntity() {
        return ResponseEntity.ok("Entity created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEntity(@PathVariable Long id) {
        return ResponseEntity.ok("Entity updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntity(@PathVariable Long id) {
        return ResponseEntity.ok("Entity deleted");
    }
}

