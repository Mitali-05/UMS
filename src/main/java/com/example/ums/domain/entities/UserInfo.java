package com.example.ums.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"roles", "grps"})
@Entity(name = "user_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer user_id;

    @Column(nullable = false, unique = true)
    String user_name;

    @Column(nullable = false, unique = true)
    String email;

    String phone_no;

    @Column(nullable = false)
    String password;

    @ManyToMany
    @JoinTable(
        name = "user_grp",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "grp_id")
    )
    Set<UserGrpInfo> grps =new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<RoleInfo> roles =new HashSet<>();
}
