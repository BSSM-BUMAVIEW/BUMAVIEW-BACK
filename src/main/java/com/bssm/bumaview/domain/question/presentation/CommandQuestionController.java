package com.bssm.bumaview.domain.question.presentation;

import com.bssm.bumaview.domain.question.application.CommandQuestionService;
import com.bssm.bumaview.domain.question.presentation.dto.response.QuestionResponse;
import com.bssm.bumaview.domain.question.presentation.dto.request.QuestionRequest;
import com.bssm.bumaview.domain.question.presentation.dto.request.QuestionUpdateRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class CommandQuestionController {

    private final CommandQuestionService commandQuestionService;

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(
            @Valid @RequestBody QuestionRequest questionRequest,
            @AuthenticationPrincipal UserDetails user
    ) {

        Long userId = Long.valueOf(user.getUsername());
        QuestionResponse questionResponse = commandQuestionService.createQuestion(userId, questionRequest);

        return ResponseEntity.ok(questionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user
    ) {
        Long userId = Long.parseLong(user.getUsername());
        String role = user.getAuthorities().stream()
                .findFirst().get().getAuthority();

        commandQuestionService.deleteQuestion(id, userId, role);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody QuestionUpdateRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        Long userId = Long.parseLong(user.getUsername());
        String role = user.getAuthorities().stream()
                .findFirst().get().getAuthority();

        QuestionResponse updated = commandQuestionService.updateQuestion(id, request, userId, role);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/sample")
    public void downloadSampleCsv(HttpServletResponse response) throws IOException {
        String fileName = "sample_questions.csv";

        // CSV 내용
        String csvContent = """
        company_name,content,category,question_at
        삼성전자,기술면접 때 포트폴리오를 자주 물어봅니다!,bank,2023
        카카오,외주 프로젝트를 하면서 어려웠던 점은 무엇인가요?,bank,2023
        네이버,1 - 1) 어쩌다 맡게 되셨나요?,bank,2023
        """;

        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        try (PrintWriter writer = response.getWriter()) {
            writer.print(csvContent);
            writer.flush();
        }
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadBulkQuestions(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId
    ) {
        commandQuestionService.uploadBulkQuestions(userId, file);
        return ResponseEntity.ok("질문들이 정상적으로 등록되었습니다.");
    }

}
