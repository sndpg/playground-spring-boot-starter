package org.psc.playground.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String TOKEN_PREFIX = "Bearer ";

    private final ObjectMapper objectMapper;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
            IOException, ServletException {
        String headerValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerValue == null || !headerValue.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(headerValue);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) throws IOException {
        if (token != null) {
            // parse the token.
            DecodedJWT decodedJwt = JWT.decode(StringUtils.removeStart(token, TOKEN_PREFIX));
            Map<String, Object> payload = objectMapper.readValue(Base64.getDecoder().decode(decodedJwt.getPayload()),
                    new TypeReference<Map<String, Object>>() {});

            if (payload != null && !payload.isEmpty() && payload.containsKey("user_name")) {
                //noinspection unchecked,MismatchedQueryAndUpdateOfCollection
                List<String> authorities = (List<String>) payload.getOrDefault("authorities", new ArrayList<String>());
                return new UsernamePasswordAuthenticationToken(payload.get("user_name"), null,
                        AuthorityUtils.createAuthorityList(authorities.toArray(new String[0])));
            }
        }
        return null;
    }
}
