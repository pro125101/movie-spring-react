package com.mycompany.movieclientshell.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;

import java.net.URI;

@Configuration
public class MovieServerRSocketConfig {

    @Profile("rsocket-tcp")
    @Bean
    RSocketRequester rSocketRequesterTcp(RSocketRequester.Builder rsocketRequesterBuilder,
                                         RSocketStrategies rSocketStrategies,
                                         @Value("${movie-server.host:localhost}") String movieServerHost,
                                         @Value("${movie-server.rsocket.port:7000}") int movieServerRSocketPort) {
        return rsocketRequesterBuilder
                .rsocketStrategies(rSocketStrategies)
                .connectTcp(movieServerHost, movieServerRSocketPort)
                .block();
    }

    @Profile("rsocket-websocket")
    @Bean
    RSocketRequester rSocketRequesterWebSocket(RSocketRequester.Builder rsocketRequesterBuilder,
                                               RSocketStrategies rSocketStrategies,
                                               @Value("${movie-server.host:localhost}") String movieServerHost,
                                               @Value("${movie-server.rest.port:8080}") int movieServerRestPort,
                                               @Value("${movie-server.rsocket.mapping-path:/rsocket}") String movieServerRSocketMappingPath) {
        return rsocketRequesterBuilder
                .rsocketStrategies(rSocketStrategies)
                .connectWebSocket(URI.create(String.format("ws://%s:%s%s", movieServerHost, movieServerRestPort, movieServerRSocketMappingPath)))
                .block();
    }

}