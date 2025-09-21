package com.bssm.bumaview.domain.question.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(name = "question_at", nullable = false)
    private String questionAt;

    public Question(Long userId, Long companyId, String content, String category, String questionAt) {
        this.userId = userId;
        this.companyId = companyId;
        this.content = content;
    }

    public void update(String content, String category, String questionAt) {
        this.content = content;
        this.category = category;
        this.questionAt = questionAt;
    }


    public static Question of(Long userId, Long companyId, String content, String category, String questionAt) {
        return new Question(userId, companyId, content, category, questionAt);
    }

}
