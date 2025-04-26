package com.malex.model;

import java.util.List;

public record UserPage(List<User> users, int total) {}
