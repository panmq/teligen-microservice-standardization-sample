package com.teligen.integration.transformer;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.handler.annotation.Headers;

import com.teligen.domain.CommonItem;

public class CommonItemTransformer {

	private static final Logger LOGGER = Logger.getLogger(CommonItemTransformer.class);

	@Transformer
	public CommonItem transformCommonItemFromHeaders(CommonItem item, @Headers Map<String, Object> headerMap) {
		item.setTopic(headerMap.get("topic").toString());
		if (headerMap.get("partition") != null) {
			item.setPartition(Integer.parseInt(headerMap.get("partition").toString()));
		}
		if (headerMap.get("key") != null) {
			item.setKey(Integer.valueOf(headerMap.get("key").toString()));
		}
		item.setData(headerMap.get("data").toString());
		return item;
	}

}
