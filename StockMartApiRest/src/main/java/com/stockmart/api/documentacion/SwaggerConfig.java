package com.stockmart.api.documentacion;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "StockMart API",
                version = "1.0",
                description = "Documentación de la API REST de StockMart para gestión de inventario, productos, ventas, etc."
        )
)

public class SwaggerConfig {
}
