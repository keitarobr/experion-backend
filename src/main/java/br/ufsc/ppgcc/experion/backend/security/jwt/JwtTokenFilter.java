package br.ufsc.ppgcc.experion.backend.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Validates token in request - if missing returns unauthorized
 *
 */
public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            RequestMatcher posLoginMatcher = new AntPathRequestMatcher("/auth/signin", "POST");
            RequestMatcher swaggerMatcher = new AntPathRequestMatcher("/swagger-ui**", "GET");
            RequestMatcher swaggerMatcherResources = new AntPathRequestMatcher("/webjars/**", "GET");
            RequestMatcher swaggerMatcherResourcesInternal = new AntPathRequestMatcher("/swagger-resources/**", "GET");
            RequestMatcher swaggerAPI = new AntPathRequestMatcher("/v2/api-docs/**", "GET");
            if (! posLoginMatcher.matches((HttpServletRequest) req) && ! swaggerMatcher.matches((HttpServletRequest) req) &&
                    ! swaggerMatcherResources.matches((HttpServletRequest) req) && ! swaggerMatcherResourcesInternal.matches((HttpServletRequest) req)
                    && ! swaggerAPI.matches((HttpServletRequest) req)) {
                HttpServletResponse response = (HttpServletResponse) res;
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(req, res);
    }

}
