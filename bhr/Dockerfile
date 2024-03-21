# 기본 이미지로 OpenJDK를 사용
FROM openjdk:17

# 애플리케이션 파일 복사
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 애플리케이션 실행
ENTRYPOINT ["java","-jar","/app.jar"]