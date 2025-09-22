package com.bssm.bumaview.domain.like.domain;

import com.bssm.bumaview.domain.answer.domain.Answer;
import com.bssm.bumaview.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    private Like(User user, Answer answer) {
        this.user = user;
        this.answer = answer;
    }

    public static Like create(User user, Answer answer) {
        return new Like(user, answer);
    }
}