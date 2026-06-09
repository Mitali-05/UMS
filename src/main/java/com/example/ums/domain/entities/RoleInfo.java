package com.example.ums.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"users", "grps", "permissions"})
@Entity(name="role_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer role_id;
    String role_name;
    String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updated_at;

    @ManyToMany(mappedBy = "roles")
    Set<UserInfo> users = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    Set<UserGrpInfo> grps = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    Set<PermissionInfo> permissions =new HashSet<>();
}
