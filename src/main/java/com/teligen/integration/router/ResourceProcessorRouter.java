package com.teligen.integration.router;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.integration.annotation.Router;

import com.teligen.domain.CommonItem;

public class ResourceProcessorRouter {
	
	private static final Logger LOGGER = Logger.getLogger(ResourceProcessorRouter.class);

	@Router
	public List<String> routeJobExecution(CommonItem commonItem) {

		final List<String> routeToChannels = new ArrayList<String>();

		routeToChannels.add(commonItem.getResourceType() + "ProcessorChain");

		return routeToChannels;
	}
}
