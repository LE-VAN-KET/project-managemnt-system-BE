package com.dut.team92.issuesservice.configuration;

import com.dut.team92.common.filter.AuthenticationTokenFilter;
import com.dut.team92.common.security.handler.AccessDeniedHandlerResolver;
import com.dut.team92.common.security.handler.UnauthorizedEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final UnauthorizedEntryPoint unauthorizedEntryPoint;
    private final AccessDeniedHandlerResolver accessDeniedHandlerResolver;
    private final AuthenticationTokenFilter authenticationJwtTokenFilter;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().antMatchers("/actuator/health")
                .antMatchers("/webjars/**")
                .antMatchers("/favicon.ico")
                .antMatchers("/v1/api-docs/**", "/swagger-ui-custom.html", "/swagger-ui/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers(h -> h.contentSecurityPolicy("script-src 'self'"))
            // enable cors and prevent CSRF
//            .cors().configurationSource(corsConfigurationSource()).and().csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .cors().configurationSource(corsConfigurationSource()).and().csrf(AbstractHttpConfigurer::disable)
            // set session management stateless
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // set unauthorized request exception handler
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(this.unauthorizedEntryPoint)
                    .accessDeniedHandler(accessDeniedHandlerResolver)
            )
            .headers(h -> h.frameOptions().sameOrigin())
            // set permission on endpoints
            .authorizeHttpRequests(a -> a
                    .antMatchers("/api/issues/**").permitAll()
                    .anyRequest().authenticated()
            )
            .logout(out -> out
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            )
            // Add jwt token filter to validate tokens with every request
            .addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
