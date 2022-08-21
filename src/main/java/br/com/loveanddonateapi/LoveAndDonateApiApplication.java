package br.com.loveanddonateapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication()
@RestController
@EnableSwagger2
public class LoveAndDonateApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoveAndDonateApiApplication.class, args);
	}

	@Bean
	public Docket docket(){
		return new Docket( DocumentationType.SWAGGER_2 )
				.select()
				.apis( RequestHandlerSelectors.basePackage( "br.com.loveanddonateapi.controller" ) )
				.paths( PathSelectors.any() )
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		//construtor depreciado, quebra o galho. Melhorar depois.
		return new ApiInfo("ame-e-doe-api",
				"API desenvolvida para o Projeto Integrador 4º Semestre",
				"2.6.5",
				null,
				null,
				null,
				null);
	}

}
