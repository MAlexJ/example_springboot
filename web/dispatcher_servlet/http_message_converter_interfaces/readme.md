### Http Message Converter

Http Message Converter is responsible for serializing Java Object to JSON/XML data representation
a nd deserializing JSON/XML data representation to Java Object.

link: https://sivakumar-hybris-dev.medium.com/spring-framework-recap-mvc-module-864d5f89b224

```
public class WebMvcConfigurationSupport implements ApplicationContextAware, ServletContextAware {

    .....

    protected final void addDefaultHttpMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new ResourceRegionHttpMessageConverter());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        
        .....
        
        if (jackson2Present) {
            builder = Jackson2ObjectMapperBuilder.json();
            if (this.applicationContext != null) {
                builder.applicationContext(this.applicationContext);
            }

            messageConverters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        } else if (gsonPresent) {
            messageConverters.add(new GsonHttpMessageConverter());
        } else if (jsonbPresent) {
            messageConverters.add(new JsonbHttpMessageConverter());
        }
        .....
        
      }  
}      
```

#### Understanding HttpMessageConverter in Spring

link: https://www.springcloud.io/post/2022-02/understand-spring-httpmessageconverter/#gsc.tab=0

The Http Message Converter is involved in a complete client-side request to server-side response process as follows.

1. View the `Content-Type` of the `request header`.
2. Finding the appropriate `HttpMessageConverter` based on the media type of the Content-Type.
3. `Deserialize` request data to Java Object.
4. Get request parameters and path variables (Path Variable)
5. Business Logic
6. Determine the `Accept header` (based on the content negotiation policy, explained below)
7. Find the appropriate `HttpMessageConverter` based on the Accept header
8. Return the `response` to the client

#### Content Negotiation

When `client requests `data from the server, `server needs to decide` in which form it will
`return the data to the client`, and this decision is `called Content Negotiation`.

The server-side decision on what form to return the data relies on the ContentNegotiationStrategy, which Spring has by
default or can be customized through configuration.