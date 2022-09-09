FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.14.1_1-slim as stage1

RUN addgroup --system retailstore && adduser -S -s /bin/false -G retailstore retailstore


VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN mkdir -p /app/dependency
WORKDIR /app/dependency
RUN chown -R retailstore:retailstore /app/dependency
USER retailstore
RUN jar -xf ../../app.jar

FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.14.1_1-slim
ARG DEPENDENCY=app/dependency
COPY --from=stage1 ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=stage1 ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=stage1 ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8080
ENTRYPOINT ["java","-cp","app:app/lib/*","technology.assessment.app.AppApplication"]
