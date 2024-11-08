package Nicolo_Mecca.Progetto_W6.security;

import Nicolo_Mecca.Progetto_W6.excepetions.UnauthorizedException;
import Nicolo_Mecca.Progetto_W6.tools.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {
    @Autowired
    private JWT jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null && isPublicGetEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Inserire token nell'Authorization Header nel formato corretto!");
        }

        String accessToken = authHeader.substring(7);
        jwt.verifyToken(accessToken);

        filterChain.doFilter(request, response);
    }

    private boolean isPublicGetEndpoint(HttpServletRequest request) {
        return request.getMethod().equals("GET") &&
                request.getRequestURI().startsWith("/events");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/auth/");
    }
}