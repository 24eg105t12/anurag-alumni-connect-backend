package edu.anurag.alumni.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getAdminStats() {
        return ResponseEntity.ok(Map.of(
            "totalUsers", 4,
            "studentsCount", 2,
            "alumniCount", 2
        ));
    }

    @PutMapping("/users/{id}/verify")
    public ResponseEntity<Void> toggleVerify(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}/toggle-active")
    public ResponseEntity<Void> toggleActive(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }
}
