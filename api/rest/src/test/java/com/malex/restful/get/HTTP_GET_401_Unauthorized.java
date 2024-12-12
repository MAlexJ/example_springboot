package com.malex.restful.get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.malex.restful.repository.UserRepository;

/*
 * Use GET requests to retrieve resource representation/information only – and not modify it in any way.
 *
 * Save:
 * An HTTP method is safe if it doesn't alter the state of the server.
 * In other words, a method is safe if it leads to a read-only operation.
 * Several common HTTP methods are safe: GET, HEAD, or OPTIONS. All safe methods are also idempotent,
 * but not all idempotent methods are safe. For example, PUT and DELETE are both idempotent but unsafe.
 *
 * Idempotency:
 * As GET requests do not change the resource’s state, these are said to be safe methods.
 * Additionally, GET APIs should be idempotent. Making multiple identical requests must produce the same result
 * every time until another API (POST or PUT) has changed the state of the resource on the server.
 *
 * GET API Response Codes:
 *
 * 200 (OK): For any given HTTP GET API, if the resource is found on the server, then it must return HTTP response
 * code 200 (OK) along with the response body, which is usually either XML or JSON content.
 *
 * 404 (NOT FOUND): In case the resource is NOT found on the server then API must
 * return HTTP response code 404 (NOT FOUND).
 *
 * 400 (BAD REQUEST): Similarly, if it is determined that the GET request itself is not correctly formed then
 * the server will return the HTTP response code 400 (BAD REQUEST).
 */
@WebMvcTest
public class HTTP_GET_401_Unauthorized {

    @Autowired
    private MockMvc mvc;

    @MockitoBean(name = "userRepository")
    private UserRepository repository;


}