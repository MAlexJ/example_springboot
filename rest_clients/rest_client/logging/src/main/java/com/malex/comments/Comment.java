package com.malex.comments;

/*
 "postId": 1,
 "id": 1,
 "name": "string",
 "email": "string",
 "body" : "string"
*/
public record Comment(Integer postId, Integer id, String name, String email, String body) {}
