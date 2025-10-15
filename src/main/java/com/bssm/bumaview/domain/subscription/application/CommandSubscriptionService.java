package com.bssm.bumaview.domain.subscription.application;

import com.bssm.bumaview.domain.question.domain.Question;
import com.bssm.bumaview.domain.question.domain.repository.QuestionRepository;
import com.bssm.bumaview.domain.subscription.application.exception.SubscriptionNotFoundException;
import com.bssm.bumaview.domain.subscription.application.mail.MailService;
import com.bssm.bumaview.domain.subscription.domain.MailSubscription;
import com.bssm.bumaview.domain.subscription.domain.repository.MailSubscriptionRepository;
import com.bssm.bumaview.domain.user.domain.User;
import com.bssm.bumaview.domain.user.exception.UserNotFoundException;
import com.bssm.bumaview.domain.user.domain.repository.UserRepository;
import com.bssm.bumaview.global.annotation.CustomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@CustomService
@RequiredArgsConstructor
public class CommandSubscriptionService {
    private final MailSubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;           // 유저 이메일 조회용
    private final QuestionRepository questionRepository;
    private final MailService mailService;

    public void subscribe(Long loginUserId, String category) {
        User user = userRepository.findById(loginUserId)
                .orElseThrow(()-> UserNotFoundException.EXCEPTION);

        MailSubscription sub = subscriptionRepository.findByUserIdAndCategory(loginUserId, category)
                .orElseGet(() -> MailSubscription.builder()
                        .userId(loginUserId)
                        .email(user.getEmail())
                        .category(category)
                        .active(true)
                        .build());

        sub.activate();
        subscriptionRepository.save(sub);
    }

    public void unsubscribe(Long loginUserId, String category) {
        MailSubscription sub = subscriptionRepository.findByUserIdAndCategory(loginUserId, category)
                .orElseThrow(() -> SubscriptionNotFoundException.EXCEPTION);
        sub.deactivate();
        subscriptionRepository.save(sub);
    }

    public void sendDailyQuestionNow(Long loginUserId) {
        List<MailSubscription> subs = subscriptionRepository.findAllByUserId(loginUserId);

        subs.stream()
                .filter(MailSubscription::isActive)
                .forEach(this::sendDailyQuestionTo);
    }

    /**
     * 매일 보낼 때 사용
     */
    public void sendDailyQuestionTo(MailSubscription sub) {
        String email = sub.getEmail();
        String category = sub.getCategory();

        Question question = getRandomQuestionByCategory(category);

        String html = generateHtmlEmail(question);
        log.info("📧 메일 발송 대상: {}", email);
        log.info("📌 질문: {}", question.getContent());

        mailService.sendHtml(email, "[부마뷰] 오늘의 면접 질문입니다!", html);
    }

    private Question getRandomQuestionByCategory(String category) {
        List<Question> questions = questionRepository.findAllByCategory(category);

        if (questions.isEmpty()) {
            throw new IllegalStateException("해당 카테고리의 질문이 존재하지 않습니다.");
        }

        int index = (int) (Math.random() * questions.size());
        return questions.get(index);
    }


    private String generateHtmlEmail(Question question) {
        String htmlTemplate = """
        <!DOCTYPE html>
        <html lang="ko">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>부마뷰 - 면접 질문</title>
            <style>
                @import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap');
                
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                }
                
                body {
                    font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
                    line-height: 1.6;
                    color: #1e293b;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    padding: 20px;
                    min-height: 100vh;
                }
                
                .email-container {
                    max-width: 600px;
                    margin: 0 auto;
                    background: white;
                    border-radius: 20px;
                    box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
                    overflow: hidden;
                }
                
                .header {
                    background: linear-gradient(135deg, #3b82f6 0%, #8b5cf6 100%);
                    padding: 40px 30px;
                    text-align: center;
                    position: relative;
                    overflow: hidden;
                }
                
                .logo {
                    position: relative;
                    z-index: 2;
                }
                
                .logo-icon {
                    font-size: 48px;
                    margin-bottom: 10px;
                    display: block;
                }
                
                .logo-text h1 {
                    color: white;
                    font-size: 28px;
                    font-weight: 700;
                    margin-bottom: 5px;
                    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                }
                
                .logo-text p {
                    color: rgba(255, 255, 255, 0.9);
                    font-size: 14px;
                    font-weight: 400;
                }
                
                .content {
                    padding: 40px 30px;
                }
                
                .greeting {
                    font-size: 18px;
                    font-weight: 600;
                    color: #1e293b;
                    margin-bottom: 20px;
                }
                
                .question-card {
                    background: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%);
                    border-radius: 16px;
                    padding: 30px;
                    margin: 30px 0;
                    border-left: 5px solid #3b82f6;
                    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
                    position: relative;
                }
                
                .question-card::before {
                    content: '❓';
                    position: absolute;
                    top: -10px;
                    right: 20px;
                    font-size: 24px;
                    background: white;
                    padding: 5px 10px;
                    border-radius: 50%;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                }
                
                .question-title {
                    font-size: 16px;
                    font-weight: 600;
                    color: #3b82f6;
                    margin-bottom: 15px;
                    text-transform: uppercase;
                    letter-spacing: 0.5px;
                }
                
                .question-text {
                    font-size: 18px;
                    line-height: 1.7;
                    color: #1e293b;
                    margin-bottom: 20px;
                }
                
                .question-meta {
                    display: flex;
                    gap: 20px;
                    flex-wrap: wrap;
                    margin-top: 20px;
                }
                
                .meta-item {
                    background: white;
                    padding: 8px 16px;
                    border-radius: 20px;
                    font-size: 12px;
                    font-weight: 500;
                    color: #64748b;
                    border: 1px solid #e2e8f0;
                }
                
                .meta-item.difficulty-medium {
                    border-color: #f59e0b;
                    color: #d97706;
                }
                
                .cta-section {
                    background: linear-gradient(135deg, #f0f9ff 0%, #e0f2fe 100%);
                    border-radius: 16px;
                    padding: 30px;
                    margin: 30px 0;
                    text-align: center;
                    border: 1px solid #0ea5e9;
                }
                
                .cta-title {
                    font-size: 20px;
                    font-weight: 700;
                    color: #0369a1;
                    margin-bottom: 10px;
                }
                
                .cta-description {
                    color: #0369a1;
                    margin-bottom: 25px;
                    font-size: 14px;
                }
                
                .cta-button {
                    display: inline-block;
                    background: linear-gradient(135deg, #3b82f6 0%, #8b5cf6 100%);
                    color: white;
                    text-decoration: none;
                    padding: 15px 30px;
                    border-radius: 50px;
                    font-weight: 600;
                    font-size: 16px;
                    box-shadow: 0 4px 15px rgba(59, 130, 246, 0.3);
                    transition: all 0.3s ease;
                }
                
                .cta-button:hover {
                    transform: translateY(-2px);
                    box-shadow: 0 8px 25px rgba(59, 130, 246, 0.4);
                }
                
                .footer {
                    background: #f8fafc;
                    padding: 30px;
                    text-align: center;
                    border-top: 1px solid #e2e8f0;
                }
                
                .footer-text {
                    color: #64748b;
                    font-size: 14px;
                    margin-bottom: 15px;
                }
                
                @media (max-width: 600px) {
                    .email-container {
                        margin: 10px;
                        border-radius: 15px;
                    }
                    
                    .header, .content, .footer {
                        padding: 20px;
                    }
                }
            </style>
        </head>
        <body>
            <div class="email-container">
                <div class="header">
                    <div class="logo">
                        <span class="logo-icon">♔</span>
                        <div class="logo-text">
                            <h1>부마뷰</h1>
                            <p>BumaView</p>
                        </div>
                    </div>
                </div>

                <div class="content">
                    <div class="greeting">안녕하세요! 새로운 면접 질문이 도착했습니다!</div>

                    <div class="question-card">
                        <div class="question-title">면접 질문</div>
                        <div class="question-text">%QUESTION%</div>
                        <div class="question-meta">
                            <span class="meta-item">카테고리: %CATEGORY%</span>
                            <span class="meta-item difficulty-medium">난이도: 보통</span>
                        </div>
                    </div>

                    <div class="cta-section">
                        <div class="cta-title">지금 바로 답변해보세요!</div>
                        <div class="cta-description">
                            당신의 답변 품질이 체스 AI의 실력을 결정합니다.<br>
                            좋은 답변을 통해 강력한 AI와 함께 승리하세요!
                        </div>
                        <a href="https://gptonline.ai/ko/" class="cta-button">답변하기</a>
                    </div>

                    <div class="footer">
                        <div class="footer-text">
                            <strong>부마뷰</strong> - 면접과 체스를 결합한 혁신적인 학습 플랫폼<br>
                            이 이메일은 부마뷰에서 발송되었습니다.
                        </div>
                    </div>
                </div>
            </div>
        </body>
        </html>
        """;

        return htmlTemplate
                .replace("%QUESTION%", question.getContent())
                .replace("%CATEGORY%", question.getCategory());
    }

}