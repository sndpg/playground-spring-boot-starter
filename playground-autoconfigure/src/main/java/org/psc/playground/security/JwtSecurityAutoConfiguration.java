package org.psc.playground.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@EnableWebSecurity
@ConditionalOnProperty(prefix = "playground", value = "enable-jwt-security", havingValue = "true", matchIfMissing = true)
public class JwtSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST).hasAnyAuthority("ROLE_USER")
                    .antMatchers(HttpMethod.GET).permitAll()
                .and()
                .addFilter(jwtAuthorizationFilter(null, null))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // @formatter:on
    }

    @Bean
    @ConditionalOnMissingBean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(
                Stream.of(HttpMethod.values()).map(Enum::toString).collect(Collectors.toList()));
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(ObjectMapper objectMapper,
            AuthenticationManager authenticationManager) {
        return new JwtAuthorizationFilter(authenticationManager, objectMapper);
    }

}
