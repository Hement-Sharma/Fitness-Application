package com.CodeWithHemant.Fitness_Tracker.security;

import com.CodeWithHemant.Fitness_Tracker.paylods.JwtErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
     JwtTokenHelper jwtTokenHelper;

    @Autowired
     ApplicationContext context;

    @Autowired
    ObjectMapper objectMapper; //to convert java object to json data or vice versa

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){

          token = authHeader.substring(7);

          try {
              userName = jwtTokenHelper.extractUserName(token);
          }
          catch (ExpiredJwtException ex){  // if token expired
              writeErrorResponse(response,401,"Unauthorized","Jwt Token Expired",request.getRequestURI());
              return;
          } catch (MalformedJwtException ex){   // MalformedJwtException: JWT is malformed (invalid structure, e.g. missing parts or corrupted token)
              writeErrorResponse(response,400,"Bad_Request","Invalid Jwt Token",request.getRequestURI());
              return;
          }catch (SignatureException ex){ //if Token signature changed (someone changed data in token)
              writeErrorResponse(response,401,"Unauthorized","Invalid Jwt Token or Token Signature changed",request.getRequestURI());
              return;
          }catch (IllegalArgumentException ex){  // IllegalArgumentException: token null or empty hai
              writeErrorResponse(response,400,"Bad_Request","Token cannot be null or empty",request.getRequestURI());
              return;
          }catch (UnsupportedJwtException ex){  //when jwt type is not supported by parser method. // UnsupportedJwtException: unsupported JWT type (e.g. unsigned token, we are sending encrypted JWT, wrong parsing method instead of pareseClaimsJws, ...)
              writeErrorResponse(response,400,"Bad_Request","Unsupported Jwt Token",request.getRequestURI());
              return;
          }

        }

       if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
           UserDetails userDetails = context.getBean(UserDetailsService.class).loadUserByUsername(userName);
           if(jwtTokenHelper.validateToken(token,userDetails)){
               UsernamePasswordAuthenticationToken authToken =
                     new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
           }
       }

        filterChain.doFilter(request,response);

    }

    private void writeErrorResponse(HttpServletResponse response,
                                    int status,
                                    String error,
                                    String message,
                                    String path) throws IOException {

        JwtErrorResponse jwtErrorResponse =
                new JwtErrorResponse(status, error, message, path);

        response.setStatus(status);
        response.setContentType("application/json");

        objectMapper.writeValue(response.getWriter(), jwtErrorResponse);//converting java object to json(because content type is json) and writing json data using writer

    }
}
