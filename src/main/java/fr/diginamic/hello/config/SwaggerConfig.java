package fr.diginamic.hello.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Recensement")
                        .version("1.0")
                        .description("Cette API fournit des données de recensement de population pour la France.")
                        .termsOfService("https://www.data.gouv.fr/fr/terms/")
                        .contact(new Contact()
                                .name("Equipe de développement")
                                .email("contact@exemple.com")
                                .url("https://www.exemple.com"))
                        .license(new License()
                                .name("Licence Open Data")
                                .url("https://www.etalab.gouv.fr/licence-ouverte-open-licence")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentation projet")
                        .url("https://github.com/CDelf/spring-boot"));
    }
}
