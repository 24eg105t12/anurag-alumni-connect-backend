package edu.anurag.alumni.repository;

import edu.anurag.alumni.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, String> {
    
    @Query("SELECT o FROM Opportunity o WHERE o.isActive = true ORDER BY o.createdAt DESC")
    List<Opportunity> findAllActive();

    @Query("SELECT o FROM Opportunity o WHERE o.isActive = true AND (:type IS NULL OR o.type = :type) ORDER BY o.createdAt DESC")
    List<Opportunity> findByTypeFiltered(@Param("type") Opportunity.JobType type);
}
