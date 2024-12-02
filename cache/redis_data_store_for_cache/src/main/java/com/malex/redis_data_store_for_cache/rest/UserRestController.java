package com.malex.redis_data_store_for_cache.rest;

import com.malex.redis_data_store_for_cache.database.service.UserService;
import com.malex.redis_data_store_for_cache.rest.request.CreateUserRequest;
import com.malex.redis_data_store_for_cache.rest.request.UpdateUserRequest;
import com.malex.redis_data_store_for_cache.rest.response.CreateUserResponse;
import com.malex.redis_data_store_for_cache.rest.response.DeleteUserResponse;
import com.malex.redis_data_store_for_cache.rest.response.FindUserByIdResponse;
import com.malex.redis_data_store_for_cache.rest.response.FindUsersResponse;
import com.malex.redis_data_store_for_cache.rest.response.UpdateUserResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserRestController {

  private final UserService userService;

  /*
   * HTTP GET
   *
   * link: https://restfulapi.net/http-methods/
   *
   * Use GET requests to retrieve resource representation/information only – and not modify it in any way.
   * As GET requests do not change the resource’s state, these are said to be safe methods.
   *
   * Additionally, GET APIs should be idempotent.
   * Making multiple identical requests must produce the same result every time until another API (POST or PUT)
   * has changed the state of the resource on the server.
   *
   */
  @GetMapping
  public ResponseEntity<FindUsersResponse> findUsers() {
    return ResponseEntity.ok(userService.findAll());
  }

  /*
   * HTTP GET
   *
   * link: https://restfulapi.net/http-methods/
   *
   * GET API Response Codes:
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
   */
  @GetMapping("/{id}")
  public ResponseEntity<FindUserByIdResponse> findUserById(@PathVariable Long id) {
    // verify request
    Objects.requireNonNull(id, "id field must not be null");

    // find user by id
    return userService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  /*
   * HTTP POST
   *
   * Use POST APIs to create new subordinate resources, e.g., a file is subordinate to a directory containing it
   * or a row is subordinate to a database table.
   *
   * Note: Please note that POST is neither safe nor idempotent, and invoking two identical POST requests
   * will result in two different resources containing the same information (except resource ids).
   *
   * POST API Response Codes
   *
   * Ideally, if a resource has been created on the origin server, the response SHOULD be HTTP response code 201 (Created)
   * and contain an entity that describes the status of the request
   * and refers to the new resource, and a Location header.
   *
   * Many times, the action performed by the POST method might not result in a resource that can be identified by a URI.
   * In this case, either HTTP response code 200 (OK)
   * or 204 (No Content) is the appropriate response status.
   */
  @PostMapping
  public ResponseEntity<CreateUserResponse> createUser(
      @RequestBody CreateUserRequest userRequest, HttpServletRequest request) {

    // verify request
    Objects.requireNonNull(userRequest, "User must not be null");

    // create user
    var userResponse = userService.save(userRequest);
    var uri = request.getRequestURI();
    var userID = userResponse.id();

    // In this case, either HTTP response code 200 (OK) or 204 (No Content) is the appropriate
    // response status
    return ResponseEntity.created(
            UriComponentsBuilder.fromUriString(uri).path("/{id}").build(userID))
        .body(userResponse);
  }

  /*
   * HTTP PUT
   *
   * Use PUT APIs primarily to update an existing resource (if the resource does not exist,
   * then API may decide to create a new resource or not).
   *
   * If the request passes through a cache and the Request-URI identifies one or more currently cached entities,
   * those entries SHOULD be treated as stale.
   * Responses to PUT method are not cacheable.
   *
   * Response Codes
   *
   * If a new resource has been created by the PUT API, the origin server MUST inform the user agent
   * via the HTTP response code 201 (Created) response.
   *
   * If an existing resource is modified, either the 200 (OK) or 204 (No Content) response codes
   * SHOULD be sent to indicate successful completion of the request.
   *
   * Example URIs
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
  public ResponseEntity<UpdateUserResponse> updateUser(
      /*
       * Example URIs:  HTTP PUT http://www.appdomain.com/users/123
       */
      @PathVariable Long id, @RequestBody UpdateUserRequest userRequest) {

    // verify request
    Objects.requireNonNull(id, "The id path variable must not be null.");
    Objects.requireNonNull(userRequest, "The user request body must not be null");

    // verify user id's
    if (!Objects.equals(id, userRequest.id())) {
      throw new IllegalArgumentException("The id path variable must match the id of the user.");
    }

    // update user
    var userResponse = userService.update(id, userRequest);

    // If an existing resource is modified, either the 200 (OK) or 204 (No Content) response codes
    return ResponseEntity.ok(userResponse);
  }

  /*
   * HTTP DELETE
   *
   * As the name applies, DELETE APIs delete the resources (identified by the Request-URI).
   *
   * DELETE operations are idempotent. If you DELETE a resource, it’s removed from the collection of resources.
   *
   * Some may argue that it makes the DELETE method non-idempotent. It’s a matter of discussion and personal opinion.
   * If the request passes through a cache and the Request-URI identifies one or more currently cached entities,
   * those entries SHOULD be treated as stale.
   * Responses to this method are not cacheable.
   *
   * link: https://restfulapi.net/rest-api-design-tutorial``-with-example/
   *
   * 1. async operation
   *  A successful response SHOULD be 202 (Accepted) if the resource has been queued for deletion (async operation),
   *
   * 2. sync operation
   *  or 200 (OK) / 204 (No Content) if the resource has been deleted permanently (sync operation).
   *
   * Response Codes:
   *
   * A successful response of DELETE requests SHOULD be an HTTP response code 200 (OK)
   * if the response includes an entity describing the status.
   *
   * The status should be 202 (Accepted) if the action has been queued.
   *
   * The status should be 204 (No Content) if the action has been performed but the response does not include an entity.
   *
   * Repeatedly calling DELETE API on that resource will not change the outcome – however,
   * calling DELETE on a resource a second time will return a 404 (NOT FOUND) since it was already removed.
   *
   * Example URIs
   *
   * HTTP DELETE http://www.appdomain.com/users/123
   * HTTP DELETE http://www.appdomain.com/users/123/accounts/456
   *
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<DeleteUserResponse> deleteUserById(@PathVariable Long id) {
    Objects.requireNonNull(id, "The id path variable must not be null");
    return userService
        .delete(id)
        /* A successful response of DELETE requests SHOULD be an HTTP response code 200 (OK)
         * if the response includes an entity describing the status.*/
        .map(ResponseEntity::ok)
        /* calling DELETE on a resource a second time will return a 404 (NOT FOUND) since it was already removed. */
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
