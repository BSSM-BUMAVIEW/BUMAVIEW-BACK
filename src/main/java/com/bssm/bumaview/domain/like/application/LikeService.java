package com.bssm.bumaview.domain.like.application;

import com.bssm.bumaview.domain.answer.domain.Answer;
import com.bssm.bumaview.domain.answer.domain.repository.AnswerRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 답변"));

        if (likeRepository.existsByUserIdAndAnswerId(userId, answerId)) {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
        }

        Like like = Like.create(user, answer);
        likeRepository.save(like);
    }

    @Transactional
    public void unlikeAnswer(Long userId, Long answerId) {
        Like like = likeRepository.findByUserIdAndAnswerId(userId, answerId)
                .orElseThrow(() -> new IllegalStateException("좋아요를 누르지 않았습니다."));

        likeRepository.delete(like);
    }
}
