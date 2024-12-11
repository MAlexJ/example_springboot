package com.malex.restful.model;

import java.util.List;

public record UserPage(List<User> users, int total) {}
