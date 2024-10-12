package com.malex.introduction_to_open_feign.model;

import lombok.Data;

@Data
public class Post {
  private Long userId;
  private Long id;
  private String title;
  private String body;
}
