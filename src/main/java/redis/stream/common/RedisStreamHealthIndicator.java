/**
 *  컨슈머에서 설정 필요
 */
//package redis.stream.common;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.stereotype.Component;
//import redis.stream.config.RedisConfig;
//
//@Component
//@RequiredArgsConstructor
//public class RedisStreamHealthIndicator implements HealthIndicator {
//    private final RedisConfig redisConfig;
//
//    @Override
//    public Health health() {
//        return (redisConfig.isSubscriptionActive() ? Health.up() : Health.down()).build();
//    }
//}
