FROM openjdk:17-jdk-oracle
COPY target/*.jar phonebook.jar
ENTRYPOINT ["java","-jar","/phonebook.jar"]