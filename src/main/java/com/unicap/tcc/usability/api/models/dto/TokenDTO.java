package com.unicap.tcc.usability.api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO {
    private String name;
    private String username;
    private String userUid;
    private String accessToken;
    private Long expirationTime;
    private String userEmail;
    private boolean isReviewer;
    private boolean isAdmin;
    private Collection<? extends GrantedAuthority> roles;
}
