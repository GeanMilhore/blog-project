FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/blog-1.0.0.jar blog.jar
EXPOSE 8080
CMD ["java", "-jar", "blog.jar"]