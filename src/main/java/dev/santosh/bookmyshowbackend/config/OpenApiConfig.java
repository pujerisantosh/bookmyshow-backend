package dev.santosh.bookmyshowbackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "BookMyShow Backend API",
                version = "1.0",
                description = "APIs for Movies, Theatres, Screens, Shows, Bookings"
        )
)
public class OpenApiConfig {


}
