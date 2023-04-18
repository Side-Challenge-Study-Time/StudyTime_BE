package com.challenge.studytime.enums;


import org.junit.jupiter.api.DisplayName;

@DisplayName("테스트 고정적인 이름을 ENUM으로 명확하게 표현")
public enum TestValidEnum {

    VALID_EMAIL("test1234@email.com"),
    VALID_PASSWORD("Aa1!bbccddeeff"),
    VALID_NAME("김무건"),
    VALID_BIRTHDAY("2000-01-01");

    private String message;

    TestValidEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
