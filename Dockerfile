# 1️⃣ JDK 21 기반 이미지
FROM openjdk:21-jdk-slim

# 2️⃣ ffmpeg 설치 (필요 시)
RUN apt-get update && apt-get install -y ffmpeg && rm -rf /var/lib/apt/lists/*

# 3️⃣ 작업 디렉토리
WORKDIR /app

# 4️⃣ 빌드된 JAR 복사
COPY build/libs/*.jar app.jar

# 5️⃣ 포트 열기
EXPOSE 8080

# 6️⃣ 실행 명령
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
