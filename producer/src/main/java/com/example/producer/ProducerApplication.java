package com.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@SpringBootApplication
@EnableBinding(ProducerChannels.class)
@RestController
public class ProducerApplication {
	private final MessageChannel producerChannel;
	private final MessageChannel directProducerChannel;

	@Autowired
	public ProducerApplication(ProducerChannels producerChannels) {
		this.producerChannel = producerChannels.consumer();
		this.directProducerChannel = producerChannels.directConsumer();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@PostMapping("/greet/{name}")
	public void publish(@PathVariable String name) {
		String greeting = "Hello, " + name + "!";

		//Same message type that you'd use in web socket support, for example.
		Message<String> msg = MessageBuilder.withPayload(greeting).build();

		producerChannel.send(msg);
	}

	@PostMapping("/greet-direct/{name}")
	public void publishDirect(@PathVariable String name) {
		String greeting = "Hello, " + name + "; This has come only to you!";

		Message<String> msg = MessageBuilder.withPayload(greeting).build();

		directProducerChannel.send(msg);
	}
}

//Arbitrary name here
interface ProducerChannels {
	//Can have multiple channels here

	@Output
	MessageChannel consumer();
	@Output
	MessageChannel directConsumer();
}
