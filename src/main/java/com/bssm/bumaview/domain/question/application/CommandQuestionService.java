package com.bssm.bumaview.domain.question.application;

import com.bssm.bumaview.domain.company.domain.Company;
import com.bssm.bumaview.domain.company.domain.repository.CompanyRepository;
import com.bssm.bumaview.domain.question.presentation.dto.response.QuestionResponse;
import com.bssm.bumaview.domain.question.exception.QuestionForbiddenException;
import com.bssm.bumaview.domain.question.exception.QuestionNotFoundException;
import com.bssm.bumaview.domain.question.domain.Question;
import com.bssm.bumaview.domain.question.domain.repository.QuestionRepository;
import com.bssm.bumaview.domain.question.presentation.dto.request.QuestionRequest;
import com.bssm.bumaview.domain.question.presentation.dto.request.QuestionUpdateRequest;
import com.bssm.bumaview.global.annotation.CustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@CustomService
@RequiredArgsConstructor
public class CommandQuestionService {

    private final QuestionRepository questionRepository;
    //리펙터링 필요 (퍼사드 패턴으로)
    private final CompanyRepository companyRepository;

    public QuestionResponse createQuestion(Long userId, QuestionRequest questionRequest) {

        //리펙터링 필요
        Company company = companyRepository.findByName(questionRequest.companyName())
                .orElseThrow();

        Question question = Question.of(
                userId,
                company,
                questionRequest.content(),
                questionRequest.category(),
                questionRequest.questionAt()
        );
        Question saved = questionRepository.save(question);
        return QuestionResponse.from(saved);
    }

    public void deleteQuestion(Long questionId, Long userId, String role) {
        Question question = getAuthorizedQuestion(questionId, userId, role);

        questionRepository.delete(question);
    }

    public QuestionResponse updateQuestion(Long questionId, QuestionUpdateRequest request, Long userId, String role) {

        Question question = getAuthorizedQuestion(questionId, userId, role);

        question.update(request.content(), request.category(), request.questionAt());
        return QuestionResponse.from(question);
    }

    private Question getAuthorizedQuestion(Long questionId, Long userId, String role) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> QuestionNotFoundException.EXCEPTION);

        if (!question.getUserId().equals(userId) && !"ADMIN".equals(role)) {
            throw QuestionForbiddenException.EXCEPTION;
        }
        return question;
    }

    @Transactional
    public void uploadBulkQuestions(Long userId, MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            List<Question> questions = reader.lines()
                    .skip(1)
                    .filter(line -> !line.isBlank())
                    .map(line -> {
                        String[] parts = line.split(",", -1);
                        if (parts.length < 4) {
                            throw new IllegalArgumentException("CSV 형식 오류: " + line);
                        }

                        String companyName = parts[0].trim();
                        String content = parts[1].trim();
                        String category = parts[2].trim();
                        String questionAt = parts[3].trim();

                        Company company = companyRepository.findByName(companyName)
                                .orElseThrow(() -> new RuntimeException("회사 정보 없음: " + companyName));

                        return Question.of(
                                userId,
                                company,
                                content,
                                category,
                                questionAt
                        );
                    })
                    .toList();

            questionRepository.saveAll(questions);
        } catch (IOException e) {
            throw new RuntimeException("파일 처리 중 오류 발생", e);
        }
    }

}
