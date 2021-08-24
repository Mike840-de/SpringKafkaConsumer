package ru.mmtr.consumer.listener;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.mmtr.consumer.dto.UserDto;

import java.util.Arrays;

@Service
@EnableKafka
@AllArgsConstructor
public class GisConsumer {

    private final WebClient webClient;

    static int count = 0;

    @KafkaListener(topics="baeldung")
//    public void msgListener(@Payload UserDto userDto, Acknowledgment acknowledgment){
//    public void msgListener(UserDto userDto){
    public void msgListener(ConsumerRecord<Long, UserDto> packageInfoEven){
        count++;
    System.out.println(count);
    UserDto userDto = packageInfoEven.value();
    packageInfoEven.headers().iterator().forEachRemaining(h -> System.out.println(h.key()+" : "+ Arrays.toString(h.value())));
    System.out.println("key: "+packageInfoEven.key());
    System.out.println("offset: "+packageInfoEven.offset());
    System.out.println("partition: "+packageInfoEven.partition());
        System.out.println(userDto.getAge());
        System.out.println(userDto.getName());
    //        acknowledgment.nack(3000);
    Mono<UserDto> mono = webClient
        .post()
        .uri("/api/v1/gis/loadMessage")
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(userDto), UserDto.class)
        .retrieve()
            .bodyToMono(UserDto.class);

        UserDto block = mono.block();
        System.out.println("Data from GIS: "+block.getName());
    }
}
