package com.resume.restcontrollers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resume.request.UserRequest;
import com.resume.response.AuthResponse;
import com.resume.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthController {
    private final ObjectMapper objectMapper;
    private final AuthService service;

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@RequestBody UserRequest user) {
        ResponseEntity<String> response = service.sendTokenRequest(user.getLogin(), user.getPassword());
        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                AuthResponse authResponse = objectMapper.readValue(response.getBody(), AuthResponse.class);
                return ResponseEntity.ok(authResponse);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse token response", e);
            }
        } else {
            throw new RuntimeException("Token exchange failed: " + response.getStatusCode());
        }
    }
}
