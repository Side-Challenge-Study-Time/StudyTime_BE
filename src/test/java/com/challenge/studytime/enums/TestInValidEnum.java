package com.challenge.studytime.enums;


import org.junit.jupiter.api.DisplayName;

@DisplayName("테스트 고정적인 이름을 ENUM으로 명확하게 표현")
public enum TestInValidEnum {

    VALID_EMAIL("invalid@email.com"),
    VALID_PASSWORD("9999"),
    VALID_NAME("무건"),
    VALID_BIRTHDAY("1212-11-11");

    private String message;

    TestInValidEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
