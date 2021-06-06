package com.pp.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {
    //访问 http://localhost:2001/u/user/info/1
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

}
