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

@RequiredArgsConstructor
@EnableWebSecurity
@ConditionalOnProperty(prefix = "playground", value = "enable-jwt-security", havingValue = "true", matchIfMissing = true)
public class JwtSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        httpSecurity.csrf().disable()
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
