package com.bssm.bumaview.domain.answer.domain.repository;

import com.bssm.bumaview.domain.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
