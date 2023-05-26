# use OpenJDK image with the latest Java version as the base image
FROM openjdk

# create a new directory for the server's docker files
RUN mkdir /server

# copy the server files from the host machine to the image filesystem
COPY out/artifacts/java_server_jar/java.server.jar /server/server.jar

# set the directory for excecuting future commands
WORKDIR /server

# run the jar file
CMD ["java", "-jar", "server.jar"]