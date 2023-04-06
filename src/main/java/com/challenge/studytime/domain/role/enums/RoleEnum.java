package com.challenge.studytime.domain.role.enums;

import lombok.Getter;

public enum RoleEnum {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MEMBER("ROLE_MEMBER"),
    ROLE_STUDY_LEADER("ROLE_STUDY_LEADER"),
    ROLE_STUDY_MEMBER("ROLE_STUDY_MEMBER");

    @Getter
    private String roleName;


    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

}
