### Hystrix Support

link: https://www.baeldung.com/spring-cloud-openfeign#hystrix

Feign supports Hystrix, so if we have enabled it, we can implement the fallback pattern.

link: https://www.baeldung.com/spring-cloud-netflix-hystrix

With the fallback pattern, when a remote service call fails, rather than generating an exception,
the service consumer will execute an alternative code path to try to carry out the action through another means.

To achieve the goal, we need to enable Hystrix by adding feign.hystrix.enabled=true in the properties file.

This allows us to implement fallback methods that are called when the service fails:

```
@Component
public class JSONPlaceHolderFallback implements JSONPlaceHolderClient {

    @Override
    public List<Post> getPosts() {
        return Collections.emptyList();
    }

    @Override
    public Post getPostById(Long postId) {
        return null;
    }
}
```

To let Feign know that fallback methods have been provided,
we also need to set our fallback class in the @FeignClient annotation:

```
@FeignClient(value = "jplaceholder",
            url = "https://jsonplaceholder.typicode.com/",
            fallback = JSONPlaceHolderFallback.class)
public interface JSONPlaceHolderClient {
    // APIs
}
```