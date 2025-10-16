# -------- Runtime Only --------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
ENV TZ=Asia/Seoul

# 로컬에서 빌드된 JAR 복사
ARG JAR_FILE=build/libs/mini-mart-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]