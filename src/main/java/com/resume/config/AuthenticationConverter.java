package com.resume.config;

//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//
//import java.util.*;
//import java.util.stream.Collectors;

//class AuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
//
//
//    @Override
//    public AbstractAuthenticationToken convert(Jwt jwt) {
//        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
//        return new JwtAuthenticationToken(jwt, authorities);
//    }
//
//    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
//        Collection<String> roles = new ArrayList<>();
//
//        // 1. Из realm_access (роли realm)
//        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
//        if (realmAccess != null) {
//            List<String> realmRoles = (List<String>) realmAccess.get("roles");
//            if (realmRoles != null) {
//                roles.addAll(realmRoles);
//            }
//        }
//
//        // 2. Из resource_access (роли клиента)
//        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
//        if (resourceAccess != null) {
//            // Для вашего клиента
//            Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("resumeClient");
//            if (clientAccess != null) {
//                List<String> clientRoles = (List<String>) clientAccess.get("roles");
//                if (clientRoles != null) {
//                    roles.addAll(clientRoles);
//                }
//            }
//        }
//
//        // 3. Преобразуем в authorities с префиксом ROLE_
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
//                .collect(Collectors.toList());
//    }
//}
