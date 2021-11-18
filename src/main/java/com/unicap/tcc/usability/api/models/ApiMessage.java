package com.unicap.tcc.usability.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.ws.rs.core.Response.Status;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiMessage {

    private Integer statusCode;
    private String statusText;
    private String message;

    public ApiMessage(Status responseStatus, String message) {
        statusCode = responseStatus.getStatusCode();
        statusText = responseStatus.getReasonPhrase();
        this.message = message;
    }
}
