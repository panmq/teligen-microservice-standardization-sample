package com.teligen.batch;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.teligen.domain.CommonItem;

public class CommonItemWriter implements ItemWriter<CommonItem> {

	private static final Logger LOGGER = Logger.getLogger(CommonItemWriter.class);

	@Autowired
	private KafkaTemplate<Integer, String> resourceKafkaTemplate;

	@Override
	public void write(List<? extends CommonItem> commonItem) throws Exception {
		for (CommonItem item : commonItem) {
			LOGGER.info("Kafka message: " + item);
			resourceKafkaTemplate.send(item.getTopic(), item.getPartition(),  item.getKey(), item.getData());
		}
	}
}
