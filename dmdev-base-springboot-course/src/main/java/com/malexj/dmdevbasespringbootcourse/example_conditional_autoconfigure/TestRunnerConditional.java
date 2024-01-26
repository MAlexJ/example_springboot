package com.malexj.dmdevbasespringbootcourse.example_conditional_autoconfigure;

import com.malexj.dmdevbasespringbootcourse.SpringRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * link to tutorial: <a
 * href="https://www.youtube.com/watch?v=8Z5bA4x5dPE&list=PLnh8EajVFTl73sJch9bJtPLnsNkTrFPbO&index=2">Spring
 * Boot. 2.1 Conditional & Autoconfigure</a>
 */
@SpringBootApplication
public class TestRunnerConditional extends SpringRunner {

  private static final String ACTIVE_PROFILE = "conditional_autoconfigure_profile";

  @Override
  public Class<?> initPrimarySourceClass() {
    return TestRunnerConditional.class;
  }

  @Override
  public String[] initArgs() {
    return applyActiveProfile(ACTIVE_PROFILE);
  }

  @Override
  public long serverUpTimeInSecond() {
    return 5;
  }
}
