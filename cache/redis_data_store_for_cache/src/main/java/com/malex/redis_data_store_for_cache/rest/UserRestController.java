package com.malex.redis_data_store_for_cache.rest;

import com.malex.redis_data_store_for_cache.database.service.UserService;
import com.malex.redis_data_store_for_cache.rest.request.UserRequest;
import com.malex.redis_data_store_for_cache.rest.response.UserResponse;
import com.malex.redis_data_store_for_cache.rest.response.UsersResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserRestController {

  private final UserService userService;

  /*
  * HTTP GET:
  *
  * Use GET requests to retrieve resource representation/information only – and not modify it in any way.
  * As GET requests do not change the resource’s state, these are said to be safe methods.
  * link: https://restfulapi.net/http-methods/
  *
  * Method Definitions:
    1. Request has body - No
    2. Successful response has body	- Yes
    3. Safe - Yes
    4. Idempotent - Yes
    5. Cacheable - Yes
  * link: https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/GET
  *
  * REST GET:
  *
  * Additionally, GET APIs should be idempotent.
  * Making multiple identical requests must produce the same result every time until another API (POST or PUT)
  * has changed the state of the resource on the server.
  *
  * Response Codes:
  *
  *  - For any given HTTP GET API, if the resource is found on the server,
  *      then it must return HTTP response code 200 (OK) – along with the response body,
  *      which is usually either XML or JSON content (due to their platform-independent nature).
  *
  *  - In case the resource is NOT found on the server then API must
  *    return HTTP response code 404 (NOT FOUND).
  *
  *  - Similarly, if it is determined that the GET request itself is not correctly
  *    formed then the server will return the HTTP response code 400 (BAD REQUEST).
  *
  * Example URIs:
  *
  * HTTP GET http://www.appdomain.com/users
  * HTTP GET http://www.appdomain.com/users?size=20&page=5
  * HTTP GET http://www.appdomain.com/users/123
  * HTTP GET http://www.appdomain.com/users/123/address
  * HTTP GET http://www.appdomain.com/users/123/address/3
  */
  @GetMapping
  public ResponseEntity<UsersResponse> findUsers() {
    return ResponseEntity.ok(userService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> findUserById(@PathVariable Long id) {
    // verify request
    Objects.requireNonNull(id, "id field must not be null");

    // find user by id
    return userService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  /*
  * HTTP POST:
  *
  * Method Definitions:
    1. Request has body	- Yes
    2. Successful response has body	- Yes
    3. Safe - No
    4. Idempotent - No
    5. Cacheable - Only if freshness information is included
  * link: https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/POST
  *
  * REST POST:
  *
  * Use POST APIs to create new subordinate resources, e.g., a file is subordinate to a directory containing it
  * or a row is subordinate to a database table.
  *
  * Note: Please note that POST is neither safe nor idempotent, and invoking two identical POST requests
  * will result in two different resources containing the same information (except resource ids).
  *
  * Response Codes:
  *
  * Ideally, if a resource has been created on the origin server, the response SHOULD be HTTP response code 201 (Created)
  * and contain an entity that describes the status of the request
  * and refers to the new resource, and a Location header.
  *
  * Many times, the action performed by the POST method might not result in a resource that can be identified by a URI.
  * In this case, either HTTP response code 200 (OK) or 204 (No Content) is the appropriate response status.
  *
  * Example URIs:
  * HTTP GET http://www.appdomain.com/users
  * body: { 'name': 'Alex'}
  */
  @PostMapping
  public ResponseEntity<UserResponse> createUser(
      @RequestBody UserRequest userRequest, HttpServletRequest request) {

    // verify request
    Objects.requireNonNull(userRequest, "User must not be null");
    Objects.requireNonNull(userRequest.username(), "username field cannot be null");

    // create user
    Optional<UserResponse> uResponse = userService.save(userRequest);

    // either HTTP response code 200 (OK) or 204 (No Content) is appropriate response status
    return uResponse
        .map(
            response -> {
              var userID = response.id();
              var uri = request.getRequestURI();
              return ResponseEntity.created(
                      UriComponentsBuilder.fromUriString(uri).path("/{id}").build(userID))
                  .body(response);
            })

        // WTF: !!!! 409 ?
        .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
  }

  /*
  * HTTP PUT
  *
  * Method Definitions:
   1. Request has body - Yes
   2. Successful response has body - May
   3. Safe - No
   4. Idempotent - Yes
   5. Cacheable - No
  * link: https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/PUT
  *
  * REST PUT:
  *
  * Use PUT APIs primarily to update an existing resource (if the resource does not exist,
  * then API may decide to create a new resource or not).
  *
  * If the request passes through a cache and the Request-URI identifies one or more currently cached entities,
  * those entries SHOULD be treated as stale.
  * Responses to PUT method are not cacheable.
  *
  * Response Codes:
  *
  * If a new resource has been created by the PUT API, the origin server MUST inform the user agent
  * via the HTTP response code 201 (Created) response.
  *
  * If an existing resource is modified, either the 200 (OK) or 204 (No Content) response codes
  * SHOULD be sent to indicate successful completion of the request.
  *
  * Example URIs:
  *
  * HTTP PUT http://www.appdomain.com/users/123
  * HTTP PUT http://www.appdomain.com/users/123/accounts/456
  *
  * Difference between PUT and POST
  *
  * link: https://restfulapi.net/rest-put-vs-post/
  *
  * 1. HTTP specification clearly mentions that PUT method requests for the attached entity
  *    (in the request body)
  *
  * 2. If the Request-URI refers to an already existing resource – an update operation will happen,
  *    otherwise create operation should happen
  *
  * 3. PUT method is idempotent.
  *    So if we retry a request multiple times, that should be equivalent to a single request invocation.
  *
  * 4. Use PUT when we want to modify a singular resource that is already a part of resource collection.
  *
  * 5. Though PUT is idempotent, we should not cache its response
  *
  * 6. Generally, in practice, use PUT for UPDATE operations.
  */
  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> updateUser(
      /*
       * Example URIs:  HTTP PUT http://www.appdomain.com/users/123
       */
      @PathVariable Long id, @RequestBody UserRequest userRequest) {

    try {
      // verify request
      Objects.requireNonNull(id, "The id path variable must not be null.");
      Objects.requireNonNull(userRequest, "The user request body must not be null");
      Objects.requireNonNull(userRequest.id(), "id field cannot be null");
      Objects.requireNonNull(userRequest.username(), "username field cannot be null");
      Objects.requireNonNull(userRequest.created(), "created field cannot be null");

      // verify user id's
      if (!Objects.equals(id, userRequest.id())) {
        throw new IllegalArgumentException("The id path variable must match the id of the user.");
      }
    } catch (Exception e) {
      log.warn("Validation error: %s".formatted(e.getMessage()));
      // validation errors: 422 UNPROCESSABLE ENTITY
      return ResponseEntity.unprocessableEntity().build();
    }

    // If an existing resource is modified, either the 200 (OK) or 204 (No Content) response codes
    return userService
        .update(id, userRequest)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.CREATED).build());
  }

  /*
   * HTTP DELETE
   * Method Definitions:
   *  1. Request has body - May
   *  2. Successful response has body - May
   *  3. Safe - No
   *  4. Idempotent - Yes
   *  5. Cacheable - No
   *  6. Allowed in HTML forms - No
   * link: https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/DELETE
   *
   * REST DELETE
   * As the name applies, DELETE APIs delete the resources (identified by the Request-URI).
   * DELETE operations are idempotent. If you DELETE a resource, it’s removed from the collection of resources.
   * Some may argue that it makes the DELETE method non-idempotent. It’s a matter of discussion and personal opinion.
   * If the request passes through a cache and the Request-URI identifies one or more currently cached entities,
   * those entries SHOULD be treated as stale.
   * Responses to this method are not cacheable.
   *
   * link: https://restfulapi.net/rest-api-design-tutorial``-with-example/
   *
   * 1. Async operation
   *  A successful response SHOULD be 202 (Accepted) if the resource has been queued for deletion (async operation),
   *
   * 2. Sync operation
   *  200 (OK) or 204 (No Content) if the resource has been deleted permanently (sync operation).
   *
   * Response Codes:
   *
   * A successful response of DELETE requests SHOULD be an HTTP response code 200 (OK)
   * if the response includes an entity describing the status (Sync operation)
   *
   * The status should be 202 (Accepted) if the action has been queued (Async operation)
   *
   * The status should be 204 (No Content) if the action has been performed but the response does not include an entity
   * (Sync operation)
   *
   * Repeatedly calling DELETE API on that resource will not change the outcome – however,
   * calling DELETE on a resource a second time will return a 404 (NOT FOUND) since it was already removed.
   *
   * Example URIs
   *
   * HTTP DELETE http://www.appdomain.com/users/123
   * HTTP DELETE http://www.appdomain.com/users/123/accounts/456
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<UserResponse> deleteUserById(@PathVariable Long id) {

    // Input parameter validation
    if (Objects.isNull(id) || id < 0) {
      log.warn("The id field must not be negative or null");
      // validation errors: 422 UNPROCESSABLE ENTITY
      return ResponseEntity.unprocessableEntity().build();
    }

    return userService
        .delete(id)
        /* A successful response of DELETE requests SHOULD be an HTTP response code 200 (OK)
         * if the response includes an entity describing the status.*/
        .map(ResponseEntity::ok)
        /* calling DELETE on a resource a second time will return a 404 (NOT FOUND) since it was already removed. */
        .orElse(ResponseEntity.notFound().build());
  }
}
