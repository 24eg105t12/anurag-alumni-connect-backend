package edu.anurag.alumni.repository;

import edu.anurag.alumni.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    
    @Query("SELECT q FROM Question q WHERE q.isActive = true ORDER BY q.createdAt DESC")
    List<Question> findAllActive();

    @Query("SELECT q FROM Question q WHERE q.isActive = true AND (:category IS NULL OR q.category = :category) ORDER BY q.createdAt DESC")
    List<Question> findByCategory(@Param("category") String category);
}
