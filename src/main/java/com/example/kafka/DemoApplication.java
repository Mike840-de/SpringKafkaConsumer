package com.example.kafka;

import com.example.kafka.dto.UserDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableKafka
public class DemoApplication {

	@KafkaListener(topics="msg")
//	public void msgListener(ConsumerRecord<Long, UserDto> record){
	public void msgListener(UserDto userDto){
		System.out.println(userDto.getAge());
		System.out.println(userDto.getName());
//		System.out.println(record.key());
//		System.out.println(record.value().getName()+" + "+record.value().getAge());
//		System.out.println(record.headers());
//		System.out.println(record.partition());
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
