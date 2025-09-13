package com.resume.services;

import com.resume.request.UserRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AuthService {
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String jwtIssuerUrl;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.keycloak.scope}")
    private String scope;

    public ResponseEntity<String> sendTokenRequest(String username, String password) {
        String tokenUrl = jwtIssuerUrl + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("username",username);
        body.add("password",password);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "password");
        body.add("scope", scope);

        var requestEntity = new HttpEntity<>(body, headers);

        return new RestTemplate().exchange(
                tokenUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
    }


}
