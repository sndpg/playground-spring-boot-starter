package org.psc.playground.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


// FIXME: usage of AuthenticationFilter seems to be buggy upon using a SessionCreationPolicy of NEVER or STATELESS,
//  since AuthenticationFilter.doFilterInternal always tries to resolve a session. This results in preserved
//  SecurityContexts between Requests and this messes things up. There also seems to be an issue (maybe related to that,
//  that this filter is also part of the response's filter chain where the filter's AuthenticationSuccessHandler
//  successHandler which then tries to redirect to "/" since the response is not yet committed at this point in the
//  chain and this leads to an exception down the road
public class JwtAuthenticationFilter extends AuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager, new JwtAuthenticationConverter());
    }

    private static class JwtAuthenticationConverter implements AuthenticationConverter {

        @Override
        public Authentication convert(HttpServletRequest request) {
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            UserDetails user = User.builder().username("user").password("").authorities(authorities).build();
            return new UsernamePasswordAuthenticationToken(user, "", authorities);
        }
    }
}
