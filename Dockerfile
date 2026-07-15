FROM eclipse-temurin:21-jre

WORKDIR /app

COPY app/target/helpy-bot-1.0.jar /app/helpy-bot.jar

CMD ["java", "-jar", "/app/helpy-bot.jar"]
