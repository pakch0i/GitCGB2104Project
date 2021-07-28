package com.cy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ScaProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScaProviderApplication.class,args);
    }
    @Value("${server.port}")
    private String server;

    @RestController
    public class ProviderController {
        @GetMapping(value = "/provider/echo/{msg}")
        public String doEcho(@PathVariable String msg) {//Echo 为回显的意思
            return server+"say:Hello Nacos Discovery " + msg;
        }
    }

}
