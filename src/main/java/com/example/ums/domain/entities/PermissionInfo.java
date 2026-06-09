package com.example.ums.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Data
@EqualsAndHashCode(exclude = {"roles"})
@Entity(name="permission_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer permission_id;
    String permission_name;
    String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updated_at;

    @ManyToMany(mappedBy = "permissions")
    Set<RoleInfo> roles = new HashSet<>();
}
