package com.malex;

import static com.malex.OpenFeignIntroductionApp.*;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * url = "https://jsonplaceholder.typicode.com/"
 */
@FeignClient(value = "openFeignWebclient", url = "${webservice.client.url}")
public interface OpenFeignWebclient {

  /*
   * @RequestMapping with method or @GetMapping
   *
   * value = "/posts"
   */
  @RequestMapping(method = RequestMethod.GET, value = "${webservice.client.path.posts.findAll}")
  List<Post> getPosts();

  /*
   * Annotation for mapping HTTP GET requests onto specific handler methods.
   *  Specifically, @GetMapping is a composed annotation that acts as a shortcut
   *  for @RequestMapping(method = RequestMethod. GET).
   *
   * Base parameters:
   *
   * name - Assign a name to this mapping.
   *
   * value - the path mapping URIs, for example, "/ profile".
   *
   * path -
   *
   * params - The parameters of the mapped request, narrowing the primary mapping.
   *    Same format for any environment: a sequence of "myParam=myValue" style expressions,
   *    with a request only mapped if each such parameter is found to have the given value.
   *    Expressions can be negated by using the "!=" operator, as in "myParam!=myValue".
   *
   * headers
   *
   * consumes -
   *
   * produces -
   */
  @GetMapping(value = "${webservice.client.path.posts.byId}", produces = "application/json")
  Post getPostById(@PathVariable("postId") Long postId);
}
