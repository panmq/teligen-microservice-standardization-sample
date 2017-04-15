package com.teligen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDiscoveryClient
public class MainApplication {

	private Log log = LogFactory.getLog(getClass());

//    @Bean
//    @DependsOn("kafkaOutboundChannelAdapter")
//    CommandLineRunner kickOff(@Qualifier("inputToKafka") MessageChannel in) {
//        return args -> {
//            for (int i = 0; i < 5; i++) {
//                in.send(new GenericMessage<>("#" + i));
//                log.info("sending message #" + i);
//            }
//        };
//    }

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
