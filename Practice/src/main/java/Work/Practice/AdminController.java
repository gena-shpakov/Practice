package Work.Practice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    private String currentToken;

    private Map<Long, String> entities = new HashMap<>();
    private long entityIdCounter = 1;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            long expirationTime = System.currentTimeMillis() + 5 * 60 * 1000; // 5 minutes
            String tokenData = username + ":" + expirationTime;
            currentToken = Base64.getEncoder().encodeToString(tokenData.getBytes(StandardCharsets.UTF_8));

            Map<String, String> response = new HashMap<>();
            response.put("token", currentToken);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
    }

    @PostMapping("/entity")
    public ResponseEntity<String> createEntity(@RequestHeader("Authorization") String token, @RequestBody String entityData) {
        if (isTokenValid(token)) {
            entities.put(entityIdCounter++, entityData);
            return ResponseEntity.ok("Entity created successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }

    @GetMapping("/entity")
    public ResponseEntity<Object> getEntities(@RequestHeader("Authorization") String token) {
        if (isTokenValid(token)) {
            return ResponseEntity.ok(entities);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }

    @PutMapping("/entity/{id}")
    public ResponseEntity<String> updateEntity(@RequestHeader("Authorization") String token,
                                               @PathVariable Long id,
                                               @RequestBody String updatedData) {
        if (isTokenValid(token)) {
            if (entities.containsKey(id)) {
                entities.put(id, updatedData);
                return ResponseEntity.ok("Entity updated successfully");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }

    @DeleteMapping("/entity/{id}")
    public ResponseEntity<String> deleteEntity(@RequestHeader("Authorization") String token,
                                               @PathVariable Long id) {
        if (isTokenValid(token)) {
            if (entities.remove(id) != null) {
                return ResponseEntity.ok("Entity deleted successfully");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }

    private boolean isTokenValid(String token) {
        if (token == null || token.isBlank() || !token.equals(currentToken)) {
            return false;
        }

        try {
            String decodedToken = new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
            String[] parts = decodedToken.split(":");
            if (parts.length != 2) {
                return false;
            }

            String username = parts[0];
            long expirationTime = Long.parseLong(parts[1]);

            return adminUsername.equals(username) && System.currentTimeMillis() <= expirationTime;
        } catch (Exception e) {
            return false;
        }
    }
}


