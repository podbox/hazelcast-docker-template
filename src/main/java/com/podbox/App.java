package com.podbox;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(exclude = {
        HazelcastAutoConfiguration.class
})
public class App {

    public static void main(final String... args) {
        run(App.class, args);
    }
}
