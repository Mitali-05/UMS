package com.example.ums.application.mapper;

import com.example.ums.application.dto.RoleSummaryDto;
import com.example.ums.application.dto.UserGrpDetails;
import com.example.ums.application.dto.UserSummaryDto;
import com.example.ums.domain.entities.RoleInfo;
import com.example.ums.domain.entities.UserGrpInfo;
import com.example.ums.domain.entities.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserGrpMapper {
    public UserGrpDetails toDto(UserGrpInfo userGrpInfo){
        UserGrpDetails userGrpDetails = UserGrpDetails.builder()
                .grp_id(userGrpInfo.getGrp_id())
                .grp_name(userGrpInfo.getGrp_name())
                .work_email(userGrpInfo.getWork_email())
                .phone_no(userGrpInfo.getPhone_no())
                .build();

        if (userGrpInfo.getUsers() != null) {
            Set<UserSummaryDto> userSummaries = userGrpInfo.getUsers().stream()
                    .map(this::toUserSummaryDto)
                    .collect(Collectors.toSet());
            userGrpDetails.setUsers(userSummaries);
        }

        if (userGrpInfo.getRoles() != null) {
            Set<RoleSummaryDto> roleSummaries = userGrpInfo.getRoles().stream()
                    .map(this::toRoleSummaryDto)
                    .collect(Collectors.toSet());
            userGrpDetails.setRoles(roleSummaries);
        }

        return userGrpDetails;
    }

    private UserSummaryDto toUserSummaryDto(UserInfo userInfo) {
        return UserSummaryDto.builder()
                .user_id(userInfo.getUser_id())
                .user_name(userInfo.getUser_name())
                .email(userInfo.getEmail())
                .phone_no(userInfo.getPhone_no())
                .build();
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

    public UserGrpInfo toEntity(UserGrpDetails userGrpDetails){
        return UserGrpInfo.builder()
                .grp_id(userGrpDetails.getGrp_id())
                .grp_name(userGrpDetails.getGrp_name())
                .work_email(userGrpDetails.getWork_email())
                .phone_no(userGrpDetails.getPhone_no())
                .build();
    }
}
