package edu.anurag.alumni.controller;

import edu.anurag.alumni.dto.ReviewDtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    @GetMapping("/my-requests")
    public ResponseEntity<List<ReviewDetailResponse>> getStudentRequests(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/open-pool")
    public ResponseEntity<List<ReviewDetailResponse>> getOpenReviewPool() {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    public ResponseEntity<ReviewDetailResponse> submitReview(
            @RequestBody CreateReviewRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/claim")
    public ResponseEntity<ReviewDetailResponse> claimReview(
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/feedback")
    public ResponseEntity<ReviewDetailResponse> submitFeedback(
            @PathVariable String id,
            @RequestBody FeedbackSubmission feedback,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().build();
    }
}
