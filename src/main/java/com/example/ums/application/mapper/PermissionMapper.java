package com.example.ums.application.mapper;

import com.example.ums.application.dto.PermissionDetails;
import com.example.ums.application.dto.RoleSummaryDto;
import com.example.ums.domain.entities.PermissionInfo;
import com.example.ums.domain.entities.RoleInfo;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.Set;

@Component
public class PermissionMapper {
    public PermissionDetails toDto(PermissionInfo permissionInfo) {
        PermissionDetails permissionDetails = PermissionDetails.builder()
                .permission_id(permissionInfo.getPermission_id())
                .permission_name(permissionInfo.getPermission_name())
                .description(permissionInfo.getDescription())
                .created_at(permissionInfo.getCreated_at())
                .updated_at(permissionInfo.getUpdated_at())
                .build();

        if (permissionInfo.getRoles() != null) {
            Set<RoleSummaryDto> roleSummaries = permissionInfo.getRoles().stream()
                    .map(this::toRoleSummaryDto)
                    .collect(Collectors.toSet());
            permissionDetails.setRoles(roleSummaries);
        }

        return permissionDetails;
    }

    private RoleSummaryDto toRoleSummaryDto(RoleInfo roleInfo) {
        return RoleSummaryDto.builder()
                .role_id(roleInfo.getRole_id())
                .role_name(roleInfo.getRole_name())
                .description(roleInfo.getDescription())
                .created_at(roleInfo.getCreated_at())
                .updated_at(roleInfo.getUpdated_at())
                .build();
    }

    public PermissionInfo toEntity(PermissionDetails permissionDetails) {
        return PermissionInfo.builder()
                .permission_id(permissionDetails.getPermission_id())
                .permission_name(permissionDetails.getPermission_name())
                .description(permissionDetails.getDescription())
                .build();
    }
}