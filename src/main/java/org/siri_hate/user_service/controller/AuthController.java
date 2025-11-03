package org.siri_hate.user_service.controller;

import org.siri_hate.user_service.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user_service/auth")
public class AuthController {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthController(JWTService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/token/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.ok(false);
        }
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        if (username == null) {
            return ResponseEntity.ok(false);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        boolean isValid = jwtService.validateToken(token, userDetails);
        return ResponseEntity.ok(isValid);
    }
}
