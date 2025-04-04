/**
 *  컨슈머에서 설정 필요
 */

//package redis.stream.config;
//
//import java.time.Duration;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.stream.ObjectRecord;
//import org.springframework.data.redis.connection.stream.StreamOffset;
//import org.springframework.data.redis.stream.StreamListener;
//import org.springframework.data.redis.stream.StreamMessageListenerContainer;
//import org.springframework.data.redis.stream.Subscription;
//import redis.stream.dto.JobDto;
//
//@Configuration
//public class RedisConfig {
//    @Value("${redis-stream-example.stream-key}")
//    private String streamKey;
//    private Subscription subscription;
//
//    @Bean
//    public Subscription subscription(RedisConnectionFactory connectionFactory, StreamListener<String, ObjectRecord<String, JobDto>> streamListener) {
//        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, JobDto>> options = StreamMessageListenerContainer
//            .StreamMessageListenerContainerOptions
//            .builder()
//            .pollTimeout(Duration.ofMillis(100))
//            .targetType(JobDto.class)
//            .batchSize(1)
//            .build();
//
//        StreamMessageListenerContainer<String, ObjectRecord<String, JobDto>> container = StreamMessageListenerContainer
//            .create(connectionFactory, options);
//
//        subscription = container.receive(
//            StreamOffset.fromStart(streamKey),
//            streamListener
//        );
//
//        container.start();
//        return subscription;
//    }
//
//    public boolean isSubscriptionActive() {
//        return subscription != null && subscription.isActive();
//    }
//}
