package com.example.springsecurity.Filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Headers","Origin,Accept,X_Requested_With,Content-Type, Access-Control-Request-Method,Access-Control-Request-Headers,authorization");
        response.addHeader("access-control-expose-headers","Access-Control-Allow-Origin,Access-Control-Allow-Origin,Access-Control-Allow-Credentials,authorization");
        response.addHeader("x-content-type-options","nosniff");
        response.addHeader("x-xss-protection","1; mode=block");
        response.addHeader("content-type","application/x-www-form-urlencoded");



        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else  {  if (request.getServletPath().equals("/refreshToken")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationToken = request.getHeader("Authorization");
            if (authorizationToken != null && authorizationToken.startsWith("Bearer")) {
                try {

                    String jwt = authorizationToken.substring(7);
                    Algorithm algorithm = Algorithm.HMAC256("mySecret1234");
                    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<GrantedAuthority> authorities = new ArrayList<>();
                    for (String r : roles) {
                        authorities.add(new SimpleGrantedAuthority(r));
                    }
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);

                } catch (Exception e) {
                    response.setHeader("error-message", e.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            } else {

                filterChain.doFilter(request, response);
            }
        } }


    }
}
