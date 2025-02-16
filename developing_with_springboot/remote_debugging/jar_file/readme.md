### Live Remote Debugging jar file

#### 1. Start Your Spring Boot Application with Remote Debugging Enabled

Run your JAR file with the following command:

```
java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:5005 -jar your-app.jar
```

Alternatively, for Java 9 and later:

```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address='*:5005' -jar your-app.jar
```

#### 2. Expose the Debugging Port (If Running on a Remote Server)

If your Spring Boot app runs on a remote server, ensure the firewall allows inbound connections to port 5005.

Example for Linux (allow incoming connections on port 5005):

```
sudo ufw allow 5005/tcp
```

#### 3. Connect from Your IDE (IntelliJ IDEA / VS Code / Eclipse)

For IntelliJ IDEA:

Go to Run → Edit Configurations.
Click + and select Remote JVM Debug.
Set the host and port (e.g., remote server IP and port 5005).
Click Apply & Debug.

#### 4. Debugging in Docker

If your Spring Boot JAR runs inside a Docker container, expose the debug port:

Modify Your Docker Run Command

```
docker run -p 8080:8080 -p 5005:5005 --name my-app my-app-image \
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar your-app.jar
```

#### 5. Debugging in Kubernetes

If your Spring Boot app runs in Kubernetes, you need to:

1. Expose port 5005 in the Deployment YAML.
2. Forward the port using kubectl port-forward:

```
kubectl port-forward pod/my-app 5005:5005
```

Then, attach your debugger to localhost:5005.