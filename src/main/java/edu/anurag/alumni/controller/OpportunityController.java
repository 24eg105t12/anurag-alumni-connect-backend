package edu.anurag.alumni.controller;

import edu.anurag.alumni.dto.OpportunityDto;
import edu.anurag.alumni.service.OpportunityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/opportunities")
@RequiredArgsConstructor
public class OpportunityController {

    private final OpportunityService opportunityService;

    @GetMapping
    public ResponseEntity<List<OpportunityDto>> getOpportunities(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) Boolean remote) {
        return ResponseEntity.ok(opportunityService.findAll(type, branch, remote));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpportunityDto> getOpportunityById(@PathVariable String id) {
        return ResponseEntity.ok(opportunityService.findById(id));
    }

    @PostMapping
    public ResponseEntity<OpportunityDto> createOpportunity(
            @Valid @RequestBody OpportunityDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails != null ? userDetails.getUsername() : "karthik.r@anurag.edu.in";
        return ResponseEntity.status(HttpStatus.CREATED).body(opportunityService.create(dto, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpportunity(
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails != null ? userDetails.getUsername() : "karthik.r@anurag.edu.in";
        opportunityService.delete(id, email);
        return ResponseEntity.noContent().build();
    }
}
