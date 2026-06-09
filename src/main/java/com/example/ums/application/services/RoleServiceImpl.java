package com.example.ums.application.services;

import com.example.ums.application.dto.RoleDetails;
import com.example.ums.application.mapper.RoleMapper;
import com.example.ums.domain.entities.PermissionInfo;
import com.example.ums.domain.entities.RoleInfo;
import com.example.ums.domain.entities.UserGrpInfo;
import com.example.ums.domain.entities.UserInfo;
import com.example.ums.domain.repositories.PermissionRepository;
import com.example.ums.domain.repositories.RoleRepository;
import com.example.ums.domain.repositories.UserGrpRepository;
import com.example.ums.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserGrpRepository userGrpRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository, UserGrpRepository userGrpRepository, PermissionRepository permissionRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userGrpRepository = userGrpRepository;
        this.permissionRepository = permissionRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDetails> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RoleDetails createRole(RoleDetails roleDetails) {
        RoleInfo roleInfo = roleMapper.toEntity(roleDetails);

        Set<UserInfo> users = roleDetails.getUsers().stream()
                .map(summaryDto -> userRepository.findById(summaryDto.getUser_id())
                        .orElseThrow(() -> new RuntimeException("User not found with ID: " + summaryDto.getUser_id())))
                .collect(Collectors.toSet());
        roleInfo.setUsers(users);

        Set<UserGrpInfo> grps = roleDetails.getGrps().stream()
                .map(summaryDto -> userGrpRepository.findById(summaryDto.getGrp_id())
                        .orElseThrow(() -> new RuntimeException("Group not found with ID: " + summaryDto.getGrp_id())))
                .collect(Collectors.toSet());
        roleInfo.setGrps(grps);

        Set<PermissionInfo> permissions = roleDetails.getPermissions().stream()
                .map(summaryDto -> permissionRepository.findById(summaryDto.getPermission_id())
                        .orElseThrow(() -> new RuntimeException("Permission not found with ID: " + summaryDto.getPermission_id())))
                .collect(Collectors.toSet());
        roleInfo.setPermissions(permissions);

        RoleInfo savedEntity = roleRepository.save(roleInfo);
        return roleMapper.toDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDetails> getBulkRoles(ArrayList<String> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return getAllRoles();
        }
        return roleIds.stream()
                .map(id -> roleRepository.findById(Integer.parseInt(id))
                        .orElseThrow(() -> new RuntimeException("Role not found: " + id)))
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDetails getRoleById(String id) {
        Integer roleId = Integer.parseInt(id);
        RoleInfo roleInfo = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
        return roleMapper.toDto(roleInfo);
    }

    @Override
    @Transactional
    public RoleDetails updateRoleById(String id, RoleDetails roleDetails) {
        Integer roleId = Integer.parseInt(id);
        RoleInfo existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));

        existingRole.setRole_name(roleDetails.getRole_name());
        existingRole.setDescription(roleDetails.getDescription());

        RoleInfo updatedEntity = roleRepository.save(existingRole);
        return roleMapper.toDto(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteRoleById(String id) {
        Integer roleId = Integer.parseInt(id);
        if (!roleRepository.existsById(roleId)) {
            throw new RuntimeException("Role not found with ID: " + id);
        }
        roleRepository.deleteById(roleId);
    }

}

