package com.bssm.bumaview.domain.answer.application;

import com.bssm.bumaview.domain.answer.application.dto.AnswerResponse;
import com.bssm.bumaview.domain.answer.domain.Answer;
import com.bssm.bumaview.domain.answer.domain.repository.AnswerRepository;
import com.bssm.bumaview.domain.answer.presentation.dto.AnswerRequest;
import com.bssm.bumaview.domain.question.application.exception.QuestionNotFoundException;
import com.bssm.bumaview.domain.question.domain.Question;
import com.bssm.bumaview.domain.question.domain.repository.QuestionRepository;
import com.bssm.bumaview.domain.user.domain.User;
import com.bssm.bumaview.domain.user.domain.exception.UserNotFoundException;
import com.bssm.bumaview.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public AnswerResponse createAnswer(Long userId, AnswerRequest answerRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        Question question = questionRepository.findById(answerRequest.questionId())
                .orElseThrow(()-> QuestionNotFoundException.EXCEPTION);

        Answer answer = Answer.create(user, question, answerRequest.content());

        answerRepository.save(answer);

        return AnswerResponse.from(answer);
    }
}
