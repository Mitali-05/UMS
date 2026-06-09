package com.example.ums.application.mapper;

import com.example.ums.application.dto.PermissionSummaryDto;
import com.example.ums.application.dto.RoleDetails;
import com.example.ums.application.dto.UserGrpSummaryDto;
import com.example.ums.application.dto.UserSummaryDto;
import com.example.ums.domain.entities.PermissionInfo;
import com.example.ums.domain.entities.RoleInfo;
import com.example.ums.domain.entities.UserGrpInfo;
import com.example.ums.domain.entities.UserInfo;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.Set;

@Component
public class RoleMapper {
    public RoleDetails toDto(RoleInfo roleInfo) {
        RoleDetails roleDetails = RoleDetails.builder()
                .role_id(roleInfo.getRole_id())
                .role_name(roleInfo.getRole_name())
                .description(roleInfo.getDescription())
                .created_at(roleInfo.getCreated_at())
                .updated_at(roleInfo.getUpdated_at())
                .build();

        if (roleInfo.getUsers() != null) {
            Set<UserSummaryDto> userSummaries = roleInfo.getUsers().stream()
                    .map(this::toUserSummaryDto)
                    .collect(Collectors.toSet());
            roleDetails.setUsers(userSummaries);
        }

        if (roleInfo.getGrps() != null) {
            Set<UserGrpSummaryDto> grpSummaries = roleInfo.getGrps().stream()
                    .map(this::toUserGrpSummaryDto)
                    .collect(Collectors.toSet());
            roleDetails.setGrps(grpSummaries);
        }

        if (roleInfo.getPermissions() != null) {
            Set<PermissionSummaryDto> permissionSummaries = roleInfo.getPermissions().stream()
                    .map(this::toPermissionSummaryDto)
                    .collect(Collectors.toSet());
            roleDetails.setPermissions(permissionSummaries);
        }

        return roleDetails;
    }

    private UserSummaryDto toUserSummaryDto(UserInfo userInfo) {
        return UserSummaryDto.builder()
                .user_id(userInfo.getUser_id())
                .user_name(userInfo.getUser_name())
                .email(userInfo.getEmail())
                .phone_no(userInfo.getPhone_no())
                .build();
    }

    private UserGrpSummaryDto toUserGrpSummaryDto(UserGrpInfo userGrpInfo) {
        return UserGrpSummaryDto.builder()
                .grp_id(userGrpInfo.getGrp_id())
                .grp_name(userGrpInfo.getGrp_name())
                .work_email(userGrpInfo.getWork_email())
                .phone_no(userGrpInfo.getPhone_no())
                .build();
    }

    private PermissionSummaryDto toPermissionSummaryDto(PermissionInfo permissionInfo) {
        return PermissionSummaryDto.builder()
                .permission_id(permissionInfo.getPermission_id())
                .permission_name(permissionInfo.getPermission_name())
                .description(permissionInfo.getDescription())
                .created_at(permissionInfo.getCreated_at())
                .updated_at(permissionInfo.getUpdated_at())
                .build();
    }


    public RoleInfo toEntity(RoleDetails roleDetails) {
        return RoleInfo.builder()
                .role_id(roleDetails.getRole_id())
                .role_name(roleDetails.getRole_name())
                .description(roleDetails.getDescription())
                .build();
    }
}
