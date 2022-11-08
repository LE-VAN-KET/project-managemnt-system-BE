package com.dut.team92.organizationservice.configuration;

import com.dut.team92.common.filter.AuthenticationTokenFilter;
import com.dut.team92.common.security.handler.AccessDeniedHandlerResolver;
import com.dut.team92.common.security.handler.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
public class SecurityConfig {
    private UnauthorizedEntryPoint unauthorizedEntryPoint;
    private AccessDeniedHandlerResolver accessDeniedHandlerResolver;
    private AuthenticationTokenFilter authenticationJwtTokenFilter;

    @Autowired
    public SecurityConfig(UnauthorizedEntryPoint unauthorizedEntryPoint,
                          AccessDeniedHandlerResolver accessDeniedHandlerResolver,
                          AuthenticationTokenFilter authenticationJwtTokenFilter) {
        this.unauthorizedEntryPoint = unauthorizedEntryPoint;
        this.accessDeniedHandlerResolver = accessDeniedHandlerResolver;
        this.authenticationJwtTokenFilter = authenticationJwtTokenFilter;
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().antMatchers("/actuator/health")
                .antMatchers("/webjars/**")
                .antMatchers("/favicon.ico")
                .antMatchers("/api/organizations/**")
                .antMatchers("/v1/api-docs/**", "/swagger-ui-custom.html", "/swagger-ui/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.headers()
//                .contentSecurityPolicy("script-src 'self'");

        // enable cors and prevent CSRF
//        http = http.cors().and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and();
        http = http.cors().and().csrf().disable();
        // set session management stateless
        http = http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        // set unauthorized request exception handler
        http = http.exceptionHandling()
                .authenticationEntryPoint(this.unauthorizedEntryPoint)
                .accessDeniedHandler(accessDeniedHandlerResolver).and();

        http = http.headers()
                .frameOptions().sameOrigin().and();

        // set permission on endpoints
        http.authorizeHttpRequests()
                .antMatchers("/api/organizations/**").permitAll()
                .anyRequest().authenticated().and()
                .logout().invalidateHttpSession(true).deleteCookies("JSESSIONID");

        // Add jwt token filter to validate tokens with every request
        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
