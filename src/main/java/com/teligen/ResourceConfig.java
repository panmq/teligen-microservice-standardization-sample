package com.teligen;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.config.EnableIntegration;

@Profile("resource")
@Configuration
@EnableIntegration
@EnableBatchProcessing
@ImportResource("/applicationContext.xml")
public class ResourceConfig {

}
