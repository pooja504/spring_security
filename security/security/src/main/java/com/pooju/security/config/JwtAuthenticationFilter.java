package com.pooju.security.config;

import com.pooju.security.UserDetails;
import com.pooju.security.service.JasonToken;
import com.pooju.security.service.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JasonToken jasonToken;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JasonToken jasonToken, UserDetailsService userDetailsService) {
        this.jasonToken = jasonToken;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token= request.getHeader("Bearer");

        if (token==null || !token.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;

        }

        final String jwt=token.substring(7);
        final String username=jasonToken.extractUserName();

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(username!=null && authentication==null){
            UserDetails userDetails= (UserDetails) userDetailsService.loadUserByUsername(username);

            if (jasonToken.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }


        filterChain.doFilter(request,response);


    }
}
