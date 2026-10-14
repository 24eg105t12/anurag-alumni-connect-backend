package edu.anurag.alumni.service;

import edu.anurag.alumni.dto.OpportunityDto;
import edu.anurag.alumni.model.Opportunity;
import edu.anurag.alumni.model.User;
import edu.anurag.alumni.repository.OpportunityRepository;
import edu.anurag.alumni.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<OpportunityDto> findAll(String type, String branch, Boolean remote) {
        return opportunityRepository.findAllActive().stream()
                .filter(o -> type == null || o.getType().name().equalsIgnoreCase(type))
                .filter(o -> remote == null || o.getIsRemote().equals(remote))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OpportunityDto findById(String id) {
        Opportunity opp = opportunityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Opportunity not found with id: " + id));
        return mapToDto(opp);
    }

    @Transactional
    public OpportunityDto create(OpportunityDto dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Opportunity.JobType jobType = Opportunity.JobType.valueOf(dto.getType().toUpperCase().replace("-", "_"));

        Opportunity opp = Opportunity.builder()
                .postedBy(user)
                .title(dto.getTitle())
                .company(dto.getCompany())
                .type(jobType)
                .location(dto.getLocation() != null ? dto.getLocation() : "Hyderabad")
                .isRemote(dto.getIsRemote() != null ? dto.getIsRemote() : false)
                .description(dto.getDescription())
                .requirements(dto.getRequirements())
                .salaryMin(dto.getSalaryMin())
                .salaryMax(dto.getSalaryMax())
                .deadline(dto.getDeadline())
                .applyLink(dto.getApplyLink())
                .isReferralAvailable(dto.getIsReferralAvailable() != null ? dto.getIsReferralAvailable() : false)
                .isActive(true)
                .build();

        Opportunity saved = opportunityRepository.save(opp);
        return mapToDto(saved);
    }

    @Transactional
    public void delete(String id, String userEmail) {
        Opportunity opp = opportunityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Opportunity not found"));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!opp.getPostedBy().getId().equals(user.getId()) && user.getRole() != User.Role.ADMIN) {
            throw new IllegalStateException("Unauthorized to delete opportunity");
        }

        opp.setIsActive(false);
        opportunityRepository.save(opp);
    }

    private OpportunityDto mapToDto(Opportunity o) {
        String posterName = o.getPostedBy().getAlumniProfile() != null ?
                o.getPostedBy().getAlumniProfile().getName() : "Alumni";

        return OpportunityDto.builder()
                .id(o.getId())
                .title(o.getTitle())
                .company(o.getCompany())
                .type(o.getType().name().toLowerCase())
                .location(o.getLocation())
                .isRemote(o.getIsRemote())
                .description(o.getDescription())
                .requirements(o.getRequirements())
                .salaryMin(o.getSalaryMin())
                .salaryMax(o.getSalaryMax())
                .deadline(o.getDeadline())
                .applyLink(o.getApplyLink())
                .isReferralAvailable(o.getIsReferralAvailable())
                .isActive(o.getIsActive())
                .postedById(o.getPostedBy().getId())
                .postedByName(posterName)
                .createdAt(o.getCreatedAt() != null ? o.getCreatedAt().toString() : "")
                .build();
    }
}
