package com.co.TrabajoProgramacion.TiendaRopa.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new io.swagger.v3.oas.models.info.Info()
                                .title("API Tienda Ropa")
                                .version("1.0")
                                .description("Esta API permite gestionar Clientes, Productos y Ventas.")
                                .contact(new Contact().name("Equipo de la Tienda").email("soporte@tiendaropa")));
    }
}
