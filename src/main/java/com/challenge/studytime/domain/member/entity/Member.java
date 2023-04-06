package com.challenge.studytime.domain.member.entity;

import com.challenge.studytime.domain.role.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email", length = 250, nullable = false)
    private String email;

    @Column(name = "member_password", length = 60, nullable = false)
    private String password;

    @Column(name = "member_name", length = 50, nullable = false)
    private String name;

    @Column(name = "birthday", length = 20)
    private String birthday;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "member_role",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


}
