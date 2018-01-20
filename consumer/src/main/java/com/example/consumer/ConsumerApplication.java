package com.example.consumer;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.SubscribableChannel;

import java.util.logging.Logger;

@SpringBootApplication
@EnableBinding(ConsumerChannels.class)
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

	@Bean @Scope("prototype")
	Logger logger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getDeclaredType().getName());
	}

	@Bean
	IntegrationFlow integrationFlow(ConsumerChannels consumerChannels, Logger logger) {
		return IntegrationFlows.from(consumerChannels.input()).handle(String.class, (payload, headers) -> {
			logger.info("new message: " + payload);

			return null;
		}).get();
	}

	@Bean
	IntegrationFlow directIntegrationFlow(ConsumerChannels consumerChannels, Logger logger) {
		return IntegrationFlows.from(consumerChannels.directInput()).handle(String.class, ((payload, headers) -> {
			logger.info("new direct message: " + payload);

			return null;
		})).get();
	}
}

interface ConsumerChannels {
	@Input("consumer")
	SubscribableChannel input();
	@Input("directConsumer")
	SubscribableChannel directInput();
}