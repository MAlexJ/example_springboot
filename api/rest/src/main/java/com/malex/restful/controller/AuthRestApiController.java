package com.malex.restful.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malex.restful.model.UserPage;
import com.malex.restful.repository.UserRepository;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthRestApiController {

    private final UserRepository repository;

    @GetMapping("/auth-endpoint/clients")
    public ResponseEntity<UserPage> authorizationFindAll(

            // base annotation for rest api
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String auth) {

        // example: Authorization: Basic token_base64
        if (isNotAuthorized(auth)) {
            return ResponseEntity.status(UNAUTHORIZED).build();
        }

        // only for admin
        if (isNotAdmin(auth)) {
            return ResponseEntity.status(FORBIDDEN).build();
        }

        var users = repository.findAll();
        var page = new UserPage(users, users.size());
        return ResponseEntity.ok(page);
    }

    private boolean isNotAdmin(String auth) {
        return !auth.replace("Basic ", "").equalsIgnoreCase("admin");
    }

    boolean isNotAuthorized(String auth) {
        if (auth.isBlank()) {
            return true;
        }
        return !auth.startsWith("Basic");
    }

}
