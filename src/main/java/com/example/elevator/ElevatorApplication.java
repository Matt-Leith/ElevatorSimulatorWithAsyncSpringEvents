package com.example.elevator;

import com.example.elevator.dao.ElevatorRepository;
import com.example.elevator.model.Elevator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ElevatorApplication {

    @Autowired
    private ElevatorRepository elevatorRepository;

	public static void main(String[] args) {
		SpringApplication.run(ElevatorApplication.class, args);
	}

	@Bean
	public Docket elevatorApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.example.elevator")).build();
	}

    @Bean
    public void setupElevators() {
        createElevator("Elevator One", 0L);
        createElevator("Elevator Two", 1L);
        createElevator("Elevator Three", 2L);
        createElevator("Elevator Four", 0L);
    }

    private void createElevator(String elevatorName, Long startFloor) {
        Elevator elevator = new Elevator();
        elevator.setName(elevatorName);
        elevator.setCurrentFloor(startFloor);
        elevatorRepository.save(elevator);
    }
}
