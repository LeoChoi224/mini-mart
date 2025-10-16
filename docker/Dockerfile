# -------- Runtime Only --------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
ENV TZ=Asia/Seoul

# 로컬에서 빌드된 JAR 복사
COPY build/libs/mini-mart.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]