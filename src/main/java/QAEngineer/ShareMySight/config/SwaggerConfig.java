package QAEngineer.ShareMySight.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
@OpenAPIDefinition(info = @Info(title = "ShareMySight", version = "1.0.0"))
@SecurityScheme(
  type = SecuritySchemeType.HTTP,
  name = "Authorization",
  description = "JWT Authorization header using the Bearer scheme.",
  scheme = "bearer",
  in = SecuritySchemeIn.HEADER,
  paramName = HttpHeaders.AUTHORIZATION
)
public class SwaggerConfig {
}
