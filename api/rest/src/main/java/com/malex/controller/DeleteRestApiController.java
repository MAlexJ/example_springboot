package com.malex.controller;

import com.malex.model.User;
import com.malex.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DeleteRestApiController {

  private final UserRepository repository;

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
   *
   * DELETE operations are idempotent. If you DELETE a resource, it’s removed from the collection of resources.
   * Some may argue that it makes the DELETE method non-idempotent. It’s a matter of discussion and personal opinion.
   *
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
   *
   * Errors:
   *
   * 422 Unprocessable Entity (rfc4918 - 11.2.) for validation errors
   * The 422 (Unprocessable Entity) status code means the server understands the content type
   * of the request entity (hence a 415 (Unsupported Media Type) status code is inappropriate),
   * and the syntax of the request entity is correct(thus a 400 (Bad Request) status code is inappropriate)
   * but was unable to process the contained instructions.
   * For example, this error condition may occur if an XML request body contains well-formed
   * (i.e., syntactically correct), but semantically erroneous, XML instructions.
   */
  @DeleteMapping("/users/{id}")
  public ResponseEntity<User> delete(@PathVariable Long id) {

    // Input parameter validation
    if (Objects.isNull(id) || id < 0) {
      log.warn("The id field must not be negative or null");
      // validation errors: 422 UNPROCESSABLE ENTITY
      return ResponseEntity.unprocessableEntity().build();
    }

    return repository
        .deleteById(id)
        /* A successful response of DELETE requests SHOULD be an HTTP response code 200 (OK)
         * if the response includes an entity describing the status.*/
        .map(ResponseEntity::ok)
        /* calling DELETE on a resource a second time will return a 404 (NOT FOUND) since it was already removed. */
        .orElse(ResponseEntity.notFound().build());
  }
}
