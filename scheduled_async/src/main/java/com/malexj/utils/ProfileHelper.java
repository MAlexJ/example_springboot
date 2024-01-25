package com.malexj.utils;


public class ProfileHelper {

  private static final String PROFILE_TEMPLATE = "--spring.profiles.active=%s";

  public static String buildActiveProfile(String profile) {
    return String.format(PROFILE_TEMPLATE, profile);
  }
}
