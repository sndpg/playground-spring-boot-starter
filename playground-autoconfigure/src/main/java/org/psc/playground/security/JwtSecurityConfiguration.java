package org.psc.playground.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@ConditionalOnProperty(prefix = "playground", value = "enable-jwt-security", havingValue = "true",
        matchIfMissing = true)
public class JwtSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .requestMatchers(request -> request.getMethod().equals(HttpMethod.POST.toString()))
                .hasAnyAuthority("POST_ALLOWED");

        httpSecurity.authorizeRequests()
                .requestMatchers(request -> request.getMethod().equals(HttpMethod.GET.toString()))
                .permitAll();
    }

}
