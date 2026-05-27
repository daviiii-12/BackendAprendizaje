package co.edu.usb.cali.reservasCanchamax.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration le avisa a Spring que aquí hay configuraciones globales que debe cargar al arrancar
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Reservas Canchamax")
                        .version("1.0.0")
                        .description("Documentación interactiva de los módulos de Canchas y Usuarios para el sistema Canchamax.")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo Canchamax")
                                .email("soporte@canchamax.com")));
    }
}