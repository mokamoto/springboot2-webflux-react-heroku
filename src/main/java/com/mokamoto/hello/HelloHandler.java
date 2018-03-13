package com.mokamoto.hello;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.time.Duration;
import java.util.stream.Stream;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class HelloHandler {

    public static final String PATH = "/hello";

    @Bean
    public RouterFunction<ServerResponse> routes() {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
        Flux<Integer> flux = Flux.fromStream(stream).delayElements(Duration.ofSeconds(1));
    
        return nest(path(PATH), 
        route(GET("/"), this::hello)
        .andRoute(GET("/{name}"), this::helloName)
        .andRoute(GET("/stream"), req -> ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(flux,Integer.class))
        );
        
    }

    public Mono<ServerResponse> hello(ServerRequest req) {
        return ok().body(Flux.just("Hello", "World!"), String.class);
    }

    public Mono<ServerResponse> helloName(ServerRequest req) {
        return ok().body(Flux.just("Hello", req.pathVariable("name")), String.class);
    }
}
