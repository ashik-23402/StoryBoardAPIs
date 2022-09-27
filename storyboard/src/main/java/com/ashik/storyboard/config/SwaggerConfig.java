package com.ashik.storyboard.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER="Authorization";
	
	
	private ApiKey apiKey() {
		
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}
	
	
	private List<SecurityContext> securityContexts() {
		
		
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
		
	}
	
	
	private List<SecurityReference> securityReferences() {
	
		AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scope}));
		
	}
	
	
	@Bean
	public Docket api() {
		
		
		
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKey()))
				.select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
				
	}

	private ApiInfo getInfo() {
		// TODO Auto-generated method stub
		return new ApiInfo("StoryBoard BackEnd ", " This project is developed for"
				+ " fall 22 software engineering course"
				+ " By Md Ashikur Rahman backend developer of Team UUMatrix ",
				"1.0.0",
				"Terms Of Service",
				new Contact("Md Ashikur Rahman", "https://github.com/ashik-23402","ashik23402@gmail.com"),
				"Licence of API", "url",
				Collections.emptyList());
	}

}
