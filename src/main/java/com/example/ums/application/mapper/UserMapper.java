package com.example.ums.application.mapper;

import com.example.ums.application.dto.RoleSummaryDto;
import com.example.ums.application.dto.UserDetails;
import com.example.ums.application.dto.UserGrpSummaryDto;
import com.example.ums.domain.entities.RoleInfo;
import com.example.ums.domain.entities.UserGrpInfo;
import com.example.ums.domain.entities.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDetails toDto(UserInfo userInfo){
        UserDetails userDetails = UserDetails.builder()
                .user_id(userInfo.getUser_id())
                .user_name(userInfo.getUser_name())
                .email(userInfo.getEmail())
                .phone_no(userInfo.getPhone_no())
                .build();

        if (userInfo.getGrps() != null) {
            Set<UserGrpSummaryDto> grpSummaries = userInfo.getGrps().stream()
                    .map(this::toUserGrpSummaryDto)
                    .collect(Collectors.toSet());
            userDetails.setGrps(grpSummaries);
        }

        if (userInfo.getRoles() != null) {
            Set<RoleSummaryDto> roleSummaries = userInfo.getRoles().stream()
                    .map(this::toRoleSummaryDto)
                    .collect(Collectors.toSet());
            userDetails.setRoles(roleSummaries);
        }

        return userDetails;
    }

    private UserGrpSummaryDto toUserGrpSummaryDto(UserGrpInfo userGrpInfo) {
        return UserGrpSummaryDto.builder()
                .grp_id(userGrpInfo.getGrp_id())
                .grp_name(userGrpInfo.getGrp_name())
                .work_email(userGrpInfo.getWork_email())
                .phone_no(userGrpInfo.getPhone_no())
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

    public UserInfo toEntity(UserDetails userDetails){
        return UserInfo.builder()
                .user_id(userDetails.getUser_id())
                .user_name(userDetails.getUser_name())
                .email(userDetails.getEmail())
                .phone_no(userDetails.getPhone_no())
                .build();
    }
}
