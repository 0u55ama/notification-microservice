//package com.osm.notificationservice.kafka;
//
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.listener.DefaultErrorHandler;
//import org.springframework.util.backoff.ExponentialBackOff;
//
//
//@Configuration
//@EnableKafka
//public class KafkaListenerConfig {
//
//    @Bean
//    public DefaultErrorHandler kafkaErrorHandler() {
//        // Use ExponentialBackOff to configure retry with increasing delay
//        ExponentialBackOff backOff = new ExponentialBackOff();
//        backOff.setInitialInterval(2000); // 2 seconds initial delay
//        backOff.setMultiplier(2); // Double the delay each time
//        backOff.setMaxInterval(60000); // 1 minute max delay
//
//        // SeekToCurrentErrorHandler is deprecated in favor of DefaultErrorHandler
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler(backOff);
//
//        // You can also add custom logic here, such as logging or sending to a dead-letter topic
//
//        return errorHandler;
//    }
//
//    @Bean(name = "kafkaListenerContainerFactory")
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(DefaultErrorHandler errorHandler) {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setCommonErrorHandler(errorHandler);
//        // Other factory configurations
//        return factory;
//    }
//}