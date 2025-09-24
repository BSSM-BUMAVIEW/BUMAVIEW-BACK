package com.bssm.bumaview.domain.like.application;

import com.bssm.bumaview.domain.answer.exception.AnswerNotFoundException;
import com.bssm.bumaview.domain.answer.domain.Answer;
import com.bssm.bumaview.domain.answer.domain.repository.AnswerRepository;
import com.bssm.bumaview.domain.like.application.exception.LikeAlreadyExistsException;
import com.bssm.bumaview.domain.like.application.exception.LikeNotFoundException;
import com.bssm.bumaview.domain.like.domain.Like;
import com.bssm.bumaview.domain.like.domain.repository.LikeRepository;
import com.bssm.bumaview.domain.user.domain.User;
import com.bssm.bumaview.domain.user.domain.exception.UserNotFoundException;
import com.bssm.bumaview.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void likeAnswer(Long userId, Long answerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> UserNotFoundException.EXCEPTION);

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> AnswerNotFoundException.EXCEPTION);

        if (likeRepository.existsByUserIdAndAnswerId(userId, answerId)) {
            throw LikeAlreadyExistsException.EXCEPTION;
        }

        Like like = Like.create(user, answer);
        likeRepository.save(like);
    }

    @Transactional
    public void unlikeAnswer(Long userId, Long answerId) {
        Like like = likeRepository.findByUserIdAndAnswerId(userId, answerId)
                .orElseThrow(()-> LikeNotFoundException.EXCEPTION);

        likeRepository.delete(like);
    }
}
