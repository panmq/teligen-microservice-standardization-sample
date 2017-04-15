package com.teligen.batch;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.teligen.domain.CommonItem;
import com.teligen.integration.gateway.ProcessorService;

public class CommonItemProcessor implements ItemProcessor<CommonItem, CommonItem> {
	
	private static final Logger LOGGER = Logger.getLogger(CommonItemProcessor.class);
	
	private String resourceType;

	@Autowired
	private ProcessorService processorService;

	@Override
	public CommonItem process(CommonItem commonItem) throws Exception {
		commonItem.setResourceType(resourceType);
		return processorService.process(commonItem);
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
}
