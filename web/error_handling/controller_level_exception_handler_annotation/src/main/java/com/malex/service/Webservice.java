package com.malex.service;

import org.springframework.stereotype.Service;

import com.malex.exception.CustomException;

@Service
public class Webservice {

  public String getInfo() {
    throw new CustomException("This is a test");
  }
}
