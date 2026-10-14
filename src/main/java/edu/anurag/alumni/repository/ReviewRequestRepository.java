package edu.anurag.alumni.repository;

import edu.anurag.alumni.model.ReviewRequest;
import edu.anurag.alumni.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRequestRepository extends JpaRepository<ReviewRequest, String> {
    
    List<ReviewRequest> findByStudentOrderByCreatedAtDesc(User student);

    @Query("SELECT r FROM ReviewRequest r WHERE r.status = 'PENDING' ORDER BY r.createdAt DESC")
    List<ReviewRequest> findOpenPool();

    List<ReviewRequest> findByReviewerOrderByCreatedAtDesc(User reviewer);
}
