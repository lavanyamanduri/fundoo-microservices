package com.bridgelabz.fundoonotes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

	
	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.bridgelabz.fundoonotes.controller")).paths(PathSelectors.any())
				.build().apiInfo(metaData());
	}
	
	
	private ApiInfo metaData() {
//		   Contact contact=new Contact("Amruth Sagar",
//		            "https://github.com/lavanyamanduri/FundooNotesApi.git","amrutha.sagar@bridgelabz.com");

		    return new ApiInfoBuilder()
		            .title("FundooNotes Application")
		            .contact(new Contact("Manduri Lavanya", "https://github.com/lavanyamanduri/FundooNotesApi",
							"lavanya.manduri@gmail.com"))
		            .description("Spring boot application for Fundoo Notes application")
		        //    .contact(contact)		          
		            .build();
	    }
	
	 @Bean
		// @LoadBalanced
		   public RestTemplate getRestTemplate() 
		   {
		      return new RestTemplate();
		   } 
}
