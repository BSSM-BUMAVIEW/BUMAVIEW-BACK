package com.bssm.bumaview.domain.answer.domain.repository;

import com.bssm.bumaview.domain.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("""
        SELECT a 
        FROM Answer a
        LEFT JOIN Like l ON l.answer = a
        GROUP BY a
        ORDER BY COUNT(l) DESC
    """)
    List<Answer> findAllOrderByLikeCountDesc();
}
