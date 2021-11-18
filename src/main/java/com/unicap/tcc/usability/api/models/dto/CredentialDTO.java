package com.unicap.tcc.usability.api.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredentialDTO {

    private String login;
    private String password;
}
