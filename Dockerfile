# FROM openjdk:17-alpine
# ARG JAR_FILE
# COPY ${JAR_FILE} app.jar
# EXPOSE 9099
# ENTRYPOINT ["java","-jar","/app.jar"]

FROM openjdk:17-alpine

# Copy the first JAR file
ARG JAR_FILE=catalog/build/libs/catalog-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Copy the second JAR file
ARG SECOND_JAR_FILE=order/build/libs/order-0.0.1-SNAPSHOT.jar
COPY ${SECOND_JAR_FILE} second.jar

# Copy the third JAR file
ARG THIRD_JAR_FILE=frontendweb/build/libs/frontendweb-0.0.1-SNAPSHOT.jar
COPY ${THIRD_JAR_FILE} third.jar

EXPOSE 9099
ENTRYPOINT ["java","-jar","/app.jar", "/second.jar", "/third.jar"]