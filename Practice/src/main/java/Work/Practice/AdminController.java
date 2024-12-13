package Work.Practice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    private String currentToken;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            long expirationTime = System.currentTimeMillis() + 5 * 60 * 1000;
            String tokenData = username + ":" + expirationTime;
            currentToken = Base64.getEncoder().encodeToString(tokenData.getBytes());

            Map<String, String> response = new HashMap<>();
            response.put("token", currentToken);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, String>> validateToken(@RequestHeader("Authorization") String token) {
        if (currentToken != null && currentToken.equals(token)) {
            return ResponseEntity.ok(Map.of("message", "Token is valid"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid or missing token"));
    }
}

