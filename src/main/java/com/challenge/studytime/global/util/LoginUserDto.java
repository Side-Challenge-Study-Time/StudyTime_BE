package com.challenge.studytime.global.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {
    private Long memberId;
    private List<String> roles = new ArrayList<>();

    public void addRole(String role){
        roles.add(role);
    }
}
