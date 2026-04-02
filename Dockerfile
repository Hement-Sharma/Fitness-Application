FROM eclipse-temurin:17.0.18_8-jdk
ADD target/Fitness-App.jar Fitness-App.jar
ENTRYPOINT ["java","-jar","/Fitness-App.jar"]