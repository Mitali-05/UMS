package com.example.ums.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"users", "roles"})
@Entity(name = "grp_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGrpInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer grp_id;
    String grp_name;
    String work_email;
    String phone_no;

    @ManyToMany(mappedBy = "grps")
    Set<UserInfo> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "grp_role",
            joinColumns = @JoinColumn(name = "grp_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<RoleInfo> roles =new HashSet<>();
}
