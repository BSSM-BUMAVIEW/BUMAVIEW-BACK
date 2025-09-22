package com.bssm.bumaview.domain.answer.domain;

import com.bssm.bumaview.domain.company.domain.Company;
import com.bssm.bumaview.domain.question.domain.Question;
import com.bssm.bumaview.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "answers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(nullable = false)
    private String content;

    public Answer(User user, Question question, String content) {
        this.user = user;
        this.question = question;
        this.content = content;
    }

    public static Answer create(User user, Question question, String content) {
        return new Answer(user, question, content);
    }

}
