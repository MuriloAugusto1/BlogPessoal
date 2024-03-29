package org.generation.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfiguration {
	
	@Bean
	public OpenAPI springOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Blog Pessoal")
						.description("This is a personal blog project!")
						.version("v0.0.1")
						.license(new License()
								.name("Generation Brazil")
								.url("https://brazil.generation.org/"))
						.contact(new Contact()
								.name("Murilo Augusto")
								.url("https://github.com/MuriloAugusto1/")
								.email("muriloalf@gmail.com")))
				.externalDocs(new ExternalDocumentation()
						.description("Github Project")
						.url("https://github.com/MuriloAugusto1/BlogPessoal"));
	}
	
	private ApiResponse createApiResponse(String message) {
		return new ApiResponse().description(message);
	}
	
	@Bean
	public OpenApiCustomiser customerGlobalResponseStatus() {
		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
				ApiResponses api = operation.getResponses();
				
				api.addApiResponse("200", createApiResponse("Sucesso!"));
				api.addApiResponse("201", createApiResponse("Criado!"));
				api.addApiResponse("400", createApiResponse("Erro de requisição!"));
				api.addApiResponse("401", createApiResponse("Não autorizado!"));
				api.addApiResponse("500", createApiResponse("Erro interno do servidor!"));
			}));
		};
	}

}