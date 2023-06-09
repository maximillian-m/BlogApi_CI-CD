package com.maximillian.blogapiwithsecurity.Config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SecuritySchemes({
        @SecurityScheme(name = "bearerToken", type = SecuritySchemeType.HTTP
                , scheme = "bearer", bearerFormat = "JWT")
})
public class SwaggerConfig{

    @Value("${application.version}")
    private String version;

    @Bean
    public OpenAPI api(){
        return new OpenAPI()
                .info(new Info()
                        .title("Fashion Blog API")
                        .description("Api that provides crud operations for a fashion blog.")
                        .version(version));
    }

    @Bean
    public GroupedOpenApi usersEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("Users")
                .pathsToMatch("/api/v1/users/**").build();
    }

    @Bean
    public GroupedOpenApi CommentEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("Comments")
                .pathsToMatch("/api/v1/comments/**").build();
    }
    @Bean
    public GroupedOpenApi PostEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("Posts")
                .pathsToMatch("/api/v1/posts/**").build();
    }
    @Bean
    public GroupedOpenApi adminEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("Admin")
                .pathsToMatch("/api/v1/Admin/**").build();
    }
    @Bean
    public GroupedOpenApi likeEndpoint(){
        return  GroupedOpenApi
                .builder()
                .group("Like")
                .pathsToMatch("/api/v1/likes/**").build();
    }
}

