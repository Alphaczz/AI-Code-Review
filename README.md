You are an expert Spring Boot developer and mentor. Help me build an 
AI-Powered Code Review & Collaboration Tool integrated with real GitHub 
repositories, step by step, phase by phase. I want to LEARN every concept 
deeply, not just get working code.

═══════════════════════════════════════════════════════════════════
PROJECT OVERVIEW
═══════════════════════════════════════════════════════════════════
Name    : AI Code Review & Collaboration Tool
Purpose : Developers open real GitHub PRs → Our app receives webhook → 
          AI reviews the actual code diff → Review posted back on GitHub → 
          Team collaborates via real-time WebSocket dashboard

Target  : Product-based company interviews (Flipkart / Swiggy level)
My Level: Intermediate — I know Spring Security + JPA well

═══════════════════════════════════════════════════════════════════
TECH STACK (100% FREE)
═══════════════════════════════════════════════════════════════════
- Java 17
- Spring Boot 3.2
- Spring Security (OAuth2 + JWT)
- Spring Data JPA + MySQL
- Spring WebSocket (STOMP)
- Spring Mail + Thymeleaf (email templates)
- Google Gemini 1.5 Flash API (free tier — AI code review)
- GitHub OAuth2 (login)
- GitHub REST API (fetch repos, PRs, diffs)
- GitHub Webhooks (trigger on PR events)
- SpringDoc OpenAPI 2.3.0 (Swagger UI)
- Lombok
- Maven

═══════════════════════════════════════════════════════════════════
ARCHITECTURE
═══════════════════════════════════════════════════════════════════

┌─────────────────────────────────────────────────────────────┐
│                     CLIENT / BROWSER                        │
│          (Postman / Swagger UI / Simple Frontend)           │
└────────────────────┬────────────────────────────────────────┘
                     │ HTTP / WebSocket
┌────────────────────▼────────────────────────────────────────┐
│                 SPRING BOOT APPLICATION                     │
│                                                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐ │
│  │  Security   │  │ Controllers │  │  WebSocket Handler  │ │
│  │  JWT Filter │→ │  REST APIs  │  │  STOMP Broker       │ │
│  │  OAuth2     │  │  Swagger    │  │                     │ │
│  └─────────────┘  └──────┬──────┘  └─────────────────────┘ │
│                          │                                  │
│  ┌───────────────────────▼────────────────────────────┐    │
│  │                 SERVICE LAYER                       │    │
│  │  AuthService  GitHubService  AIReviewService       │    │
│  │  CommentService  EmailService  NotificationService │    │
│  └───────────────────────┬────────────────────────────┘    │
│                          │                                  │
│  ┌───────────────────────▼────────────────────────────┐    │
│  │               REPOSITORY LAYER                     │    │
│  │           Spring Data JPA Repositories             │    │
│  └───────────────────────┬────────────────────────────┘    │
└──────────────────────────┼──────────────────────────────────┘
                           │
          ┌────────────────┼─────────────────┐
          ▼                ▼                 ▼
    ┌──────────┐   ┌──────────────┐   ┌──────────────┐
    │  MySQL   │   │  GitHub API  │   │  Gemini API  │
    │ Database │   │  + Webhooks  │   │  (AI Review) │
    └──────────┘   └──────────────┘   └──────────────┘
                          │
                   ┌──────▼──────┐
                   │ Gmail SMTP  │
                   └─────────────┘


PHASE 10 — Polish & Interview Prep (Days 30-31)
  Build : Logging, Spring profiles, clean Swagger docs,
          README with architecture diagram
  Learn : SLF4J + Logback, @Slf4j, log levels,
          application-dev/prod.properties, @Profile


