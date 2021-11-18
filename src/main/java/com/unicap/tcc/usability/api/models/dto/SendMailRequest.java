package com.unicap.tcc.usability.api.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SendMailRequest implements Serializable {

    private UUID assessmentUid;
    private List<String> emails;
}
