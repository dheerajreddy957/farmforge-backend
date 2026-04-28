package com.farmforge.backend.controller;
import com.farmforge.backend.model.User;
import com.farmforge.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        String name = credentials.get("name");
        String role = credentials.get("role");

        Optional<User> existing = userRepository.findByEmailAndPassword(email, password);
        if (existing.isPresent()) {
            return ResponseEntity.ok(existing.get());
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setStatus("Active");
        return ResponseEntity.ok(userRepository.save(user));
    }
}
