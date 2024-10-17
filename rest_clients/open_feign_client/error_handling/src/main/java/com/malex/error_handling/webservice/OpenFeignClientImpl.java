package com.malex.error_handling.webservice;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "openFeignClientImpl")
public interface OpenFeignClientImpl {}
