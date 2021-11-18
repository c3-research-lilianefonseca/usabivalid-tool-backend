package com.unicap.tcc.usability.api.models.enums;

import lombok.Getter;

@Getter
public enum MessageType {
    PLAN_FORM_TYPE_NOT_FOUND_ERROR("NÃO FOI POSSÍVEL IDENTIFICAR O FORMULARIO DE QUESTÕES %s");

    private String message;

    MessageType(String message) {
        this.message = message;
    }
}
