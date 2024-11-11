package com.example.configuration;

import com.malexj.autoconfigure.SignatureAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SignatureAutoConfiguration.class)
public class RunConfiguration {}
