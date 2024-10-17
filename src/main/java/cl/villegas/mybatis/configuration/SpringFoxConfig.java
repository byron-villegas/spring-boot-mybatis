package cl.villegas.mybatis.configuration;

import cl.villegas.mybatis.constants.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile("!production")
@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(Constants.Swagger.PACKAGE))
                .build()
                .apiInfo(generateApiInfo());
    }

    private ApiInfo generateApiInfo() {
        return new ApiInfoBuilder()
                .title(Constants.Swagger.TITLE)
                .description(Constants.Swagger.DESCRIPTION)
                .contact(new Contact(Constants.Swagger.NAME, Constants.Swagger.URL, Constants.Swagger.EMAIL))
                .license(Constants.Swagger.LICENSE)
                .licenseUrl(Constants.Swagger.LICENSE_URL)
                .version(Constants.Swagger.LICENSE_VERSION)
                .build();
    }
}