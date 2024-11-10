package com.malex.post;

/*
* "userId": 1,
    "id": 1,
    "title": "string",
    "body": "string"
 */
public record Post(Integer userId, Integer id, String title, String body) {}
