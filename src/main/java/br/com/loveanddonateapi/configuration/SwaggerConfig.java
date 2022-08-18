package br.com.loveanddonateapi.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@ConfigurationProperties( "app.api" )
@ConditionalOnProperty( name="app.api.swagger.enable", havingValue = "true", matchIfMissing = false )
@Getter
@Setter
public class SwaggerConfig extends WebMvcConfigurationSupport {

    private String version;
    private String title;
    private String description;
    private String basePackage;
    private String contactName;
    private String contactEmail;


    @Bean
    public Docket docket(){
        return new Docket( DocumentationType.SWAGGER_2 )
                .select()
                .apis( RequestHandlerSelectors.basePackage( basePackage ) )
                .paths( PathSelectors.any() )
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title( title )
                .description( description )
                .version( version )
                .contact( new Contact( contactName, null, contactEmail ) )
                .build();
    }

    protected void addResourceHandlers( ResourceHandlerRegistry resourceHandlerRegistry ){
        resourceHandlerRegistry.addResourceHandler( "swagger.ui" )
                .addResourceLocations( "classpath:/META-INF/resources" );

        resourceHandlerRegistry.addResourceHandler( "/webjars/**" )
                .addResourceLocations( "classpath:/META-INF/resources/webjars/" );

    }

}