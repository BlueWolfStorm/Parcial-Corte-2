package _com6.Parcial.Corte2.security.configuration;

import _com6.Parcial.Corte2.service.CustomUserDetailService;
import _com6.Parcial.Corte2.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomUserDetailService customUserDetailService;
    private final TokenService tokenService;

    @Autowired
    public JwtAuthenticationFilter(CustomUserDetailService customUserDetailService, TokenService tokenService) {
        this.customUserDetailService = customUserDetailService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(tokenService.doesNotContainBearer(authHeader)){
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = tokenService.extractToken(request);
        String email = tokenService.extractEmail(jwtToken);

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            var userDetails = customUserDetailService.loadUserByUsername(email);
            if(tokenService.isValid(jwtToken, userDetails)){
                updateContext(userDetails, request);
            }

            filterChain.doFilter(request, response);
        }
    }

    private void updateContext(UserDetails userDetails, HttpServletRequest request){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }


}
