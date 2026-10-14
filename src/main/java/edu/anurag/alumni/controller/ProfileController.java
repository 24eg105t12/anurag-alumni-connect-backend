package edu.anurag.alumni.controller;

import edu.anurag.alumni.model.AlumniProfile;
import edu.anurag.alumni.model.StudentProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/student")
    public ResponseEntity<StudentProfile> updateStudentProfile(
            @RequestBody StudentProfile profile,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/alumni")
    public ResponseEntity<AlumniProfile> updateAlumniProfile(
            @RequestBody AlumniProfile profile,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/alumni")
    public ResponseEntity<List<AlumniProfile>> getAlumniDirectory(
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(List.of());
    }
}
