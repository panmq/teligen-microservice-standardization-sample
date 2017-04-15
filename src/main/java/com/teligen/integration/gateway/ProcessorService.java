package com.teligen.integration.gateway;

import org.springframework.context.annotation.Profile;

import com.teligen.domain.CommonItem;

@Profile("resource")
public interface ProcessorService {

	CommonItem process(CommonItem item);

}
