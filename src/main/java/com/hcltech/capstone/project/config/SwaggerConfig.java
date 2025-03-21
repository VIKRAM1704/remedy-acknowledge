package com.hcltech.capstone.project.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hcltech.capstone.project.util.Constant;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

	public static final String remedy = "Remedy-Aknowledgement";

	@Bean
	public OpenAPI customOpenAPI() {
		final String securitySchemeName = Constant.AUTHORIZATION;
		License license = new License();
		license.setName(remedy);
		final String apiTitle = String.format("%s API", StringUtils.capitalize(remedy));
		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(new Components().addSecuritySchemes(securitySchemeName,
						new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer")
								.description("Access token").bearerFormat("JWT").in(SecurityScheme.In.HEADER)))
				.info(new Info().title(apiTitle).version("1.0").description(remedy).termsOfService("Ticket Service")
						.license(license));
	}
}