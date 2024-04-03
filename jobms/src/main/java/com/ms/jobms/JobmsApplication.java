package com.ms.jobms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class JobmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobmsApplication.class, args);
	}

}

/**
 * Notes:
 * 1. Create Eureka server i.e service registry
 * 2. Create individual microservice app and register it with eureka server as eureka client
 * 3. use @EnableFeignClients in the client app to use Feign configuration
 * 4. Create FeignClient and configure microservice url
 * 5. Distributed tracing zipkin default 9411
 * 6. Zipkin is log server
 * 7. config server centralized versioned configuration
 * 8. api gateway: req routing, load bal, authentication & authorization, rate limiting
 * 9. Fault tolerance
 * 		a. resilience4j
 * 10. Fallback mechanism
 * 11. Implement Rabbit MQueue to handle service down scenario
 * 12. Spring boot profiles to dockerise the application
 * 13. To create docker image ./mvnw spring-boot:build-image "-Dspring-boot.build-image.imageName=chaskarkulbhushan/jobms"
 */