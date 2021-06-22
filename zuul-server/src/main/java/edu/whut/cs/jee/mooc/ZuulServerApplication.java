package edu.whut.cs.jee.mooc;

import brave.sampler.Sampler;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author qixin on 2021/6/21.
 * @version 1.0
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "edu.whut.cs.jee.mooc.client")
@EnableCircuitBreaker
public class ZuulServerApplication {
    public static void main(String[] args) {
        int port = 8080;

        new SpringApplicationBuilder(ZuulServerApplication.class).properties("server.port=" + port).run(args);

    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
