package com.bssm.bumaview.domain.question.domain.repository;

import com.bssm.bumaview.domain.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByCategory(String category);

    List<Question> findByQuestionAt(String questionAt);

    List<Question> findByCategory(String category);

    List<Question> findByCompanyId(Long companyId);

    Long countByCategory(String category);

    Long countByQuestionAt(String questionAt);

    Long countByCompanyId(Long companyId);

    @Query(value = "SELECT * FROM questions WHERE category = :category ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Question> findRandomByCategory(@Param("category") String category);


}
