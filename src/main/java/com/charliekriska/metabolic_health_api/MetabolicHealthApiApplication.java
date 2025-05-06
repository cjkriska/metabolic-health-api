package com.charliekriska.metabolic_health_api;

import com.charliekriska.metabolic_health_api.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MetabolicHealthApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetabolicHealthApiApplication.class, args);
	}

}
