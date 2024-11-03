package com.malex.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ApiService {

  public Integer getStatus(String statusCode) {

    int code = Integer.parseInt(statusCode);
    var httpStatusCode = HttpStatusCode.valueOf(code);

    if (httpStatusCode.is4xxClientError()) {
      throw new ResponseStatusException(httpStatusCode, "Error 4xx");
    }
    if (httpStatusCode.is5xxServerError()) {
      throw new ResponseStatusException(httpStatusCode, "Error 5xx");
    }

    return code;
  }
}
