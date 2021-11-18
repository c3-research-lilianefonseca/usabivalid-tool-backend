package com.unicap.tcc.usability.api.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String login;
    @JsonIgnore
    private String email;
    private String name;
    @JsonIgnore
    private boolean admin;
    @JsonIgnore
    private boolean isEnable;
}
