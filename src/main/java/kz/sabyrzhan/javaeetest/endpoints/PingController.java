package kz.sabyrzhan.javaeetest.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping(value = "/ping")
@RestController
public class PingController {

    @GetMapping
    public Mono<ResponseEntity<String>> ping() {
        return Mono.just(new ResponseEntity<>("pong", HttpStatus.OK));
    }
}
