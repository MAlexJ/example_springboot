### Understanding HTTP Request Handling in Spring Boot

Apache Tomcat, an open-source Java servlet container, implements key Java enterprise (now Jakarta EE) standards,
including Jakarta Servlet, Jakarta Server Pages, and Jakarta WebSocket.

Currently, it’s important to understand that Spring initiates a servlet container that listens for requests
on the default TCP port 8080.

Immediately, we can observe the acceptor and worker threads handling the request using `VisualVM` profiler:

```
Thread: http-nio-Acceptor
Thread: nio-8080-exec-1
Thread: nio-8080-exec-2
Thread: nio-8080-exec-3
Thread: nio-8080-exec-4
Thread: nio-8080-exec-5
Thread: nio-8080-exec-6
Thread: nio-8080-exec-7
Thread: nio-8080-exec-8
Thread: nio-8080-exec-9
Thread: nio-8080-exec-10
```

`Acceptor threads` are responsible for receiving incoming requests and placing them into a `queue`.
However, if the `queue` reaches its capacity, acceptors will reject additional requests.
On the other hand, `worker threads` retrieve requests from the `acceptor queue `
and handle each request within their dedicated thread stack.

In our case, we currently have `1 acceptor` and 1`0 worker threads`.
However, please note that these values can vary depending on our specific configuration.

Some important configuration parameters to consider are:

```
# Maximum queue length for incoming connection requests when all possible request processing threads are in use.
server.tomcat.accept-count=100 

# Maximum number of connections that the server accepts and processes at any given time.
server.tomcat.max-connections=10000 

# Maximum amount of worker threads.
server.tomcat.max-threads=200 

# Minimum amount of worker threads.
server.tomcat.min-spare-threads=10 
```

#### Request Mapping

The central method within the `DispatcherServlet` is the `doService method`, which receives and directs our request.

```
public class DispatcherServlet extends FrameworkServlet {

 .......

    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ....
        try {
            this.doDispatch(request, response);
        } finally {
        ...
    }
 }
```

You might be wondering, how it determines the appropriate class to handle this specific request.
The answer lies within its `handlerMappings field`, which stores a collection of classes
that implement the HandlerMapping interface.

```
public class DispatcherServlet extends FrameworkServlet {

 .......

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ......
        HandlerExecutionChain mappedHandler = null;
        ....
        try {
            try {
                ModelAndView mv = null;
                Exception dispatchException = null;
                
                try {
                    ....
                    mappedHandler = this.getHandler(processedRequest);
                   
                    if (mappedHandler == null) {
                        this.noHandlerFound(processedRequest, response);
                        return;
                    }
    }
}
```

This field is initialized by the `initHandlerMappings method `during the instantiation of the `DispatcherServlet`.

```
requestMappingHandlerMapping -> {RequestMappingHandlerMapping@8167}  
           -> mappingRegistry 
                -> registry
                   -> REST Conrtoller (as AbstractHandlerMethodMapping)
```

Whenever we define a new @Controller class with method-level @RequestMapping annotations, 
Spring automatically generates a RequestMappingInfo class. 
This generated information is then seamlessly incorporated into the handlerMappings attribute. 
Subsequently, our DispatcherServlet leverages this data for precise request routing.

The remaining logic is quite straightforward. 
Utilizing the getHandler method, the DispatcherServlet iterates through all mappings in a loop:

```
public class DispatcherServlet extends FrameworkServlet {

 .......

    @Nullable
    protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        if (this.handlerMappings != null) {
            Iterator var2 = this.handlerMappings.iterator();

            while(var2.hasNext()) {
                HandlerMapping mapping = (HandlerMapping)var2.next();
                HandlerExecutionChain handler = mapping.getHandler(request);
                if (handler != null) {
                    return handler;
                }
            }
        }

        return null;
    }
}
```

The DispatcherServlet remains to pass the request to the found handler. And that’s how it works.