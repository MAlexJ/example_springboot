package com.malexj;

import static com.malexj.utils.ProfileHelper.buildActiveProfile;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VirtualThreadSchedulerConfigurerApplication extends SpringRunner {

  /** Run scheduler class {@link com.malexj.scheduler.SchedulerService} with profile */
  private static final String ACTIVE_PROFILE = "virtual-scheduling-configurer";

  @Override
  Class<?> initPrimarySourceClass() {
    return VirtualThreadSchedulerConfigurerApplication.class;
  }

  @Override
  String[] initMainArgs() {
    return new String[] {buildActiveProfile(ACTIVE_PROFILE)};
  }
}
