package com.challenge.studytime.global.initializer;

import com.challenge.studytime.domain.role.entity.Role;
import com.challenge.studytime.domain.role.enums.RoleEnum;
import com.challenge.studytime.domain.role.repositry.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                Role userRole = Role.builder()
                        .roleId(1L)
                        .name(RoleEnum.ROLE_USER.getRoleName())
                        .build();

                Role customerRole = Role.builder()
                        .roleId(2L)
                        .name(RoleEnum.ROLE_CUSTOMER.getRoleName())
                        .build();

                Role studyLeader = Role.builder()
                        .roleId(3L)
                        .name(RoleEnum.ROLE_STUDY_LEADER.getRoleName())
                        .build();

                Role studyMember = Role.builder()
                        .roleId(4L)
                        .name(RoleEnum.ROLE_STUDY_MEMBER.getRoleName())
                        .build();

                roleRepository.save(userRole);
                roleRepository.save(customerRole);
                roleRepository.save(studyLeader);
                roleRepository.save(studyMember);
            }
        };
    }
}
