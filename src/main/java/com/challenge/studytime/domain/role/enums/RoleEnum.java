package com.challenge.studytime.domain.role.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {

    ROLE_CUSTOMER("ROLE_CUSTOMER"),
    ROLE_USER("ROLE_USER"),
    ROLE_STUDY_LEADER("ROLE_STUDY_LEADER"),
    ROLE_STUDY_MEMBER("ROLE_STUDY_USER");

    private String roleName;


    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

}
