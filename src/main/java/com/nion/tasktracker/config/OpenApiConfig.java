package com.nion.tasktracker.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(
        title = "Task Tracker API",
        description = "this is advanced api for task traker app",
        version = "1.0.0",
        contact = @Contact(
                name = "MrNikaMilon",
                email = "sergnikonpav@email.com",
                url = "https://github.com/MrNikaMilon"
        )),
        servers = @Server(url = "http://localhost:8090")
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig { }
