package com.maximillian.blogapiwithsecurity.Config.SecurityConfig;

import com.maximillian.blogapiwithsecurity.Enums.Roles;
import com.maximillian.blogapiwithsecurity.utils.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtFilter;

    public final static String [] WHITE_LIST_URL
            = { "/api/v1/users/**",
                "/v3/api-docs.yaml/",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
                };

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
       return  http
                  .csrf()
               .disable()
               .authorizeHttpRequests()
               .requestMatchers(WHITE_LIST_URL)
               .permitAll()
               .and()
               .authorizeHttpRequests()
               .requestMatchers(POST,"/api/v1/Admin/**")
               .hasAuthority("ADMIN")
               .anyRequest()
               .authenticated()
               .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authenticationProvider(authenticationProvider)
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
