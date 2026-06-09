package com.example.ums.application.services;

import com.example.ums.application.dto.PermissionDetails;
import com.example.ums.application.mapper.PermissionMapper;
import com.example.ums.domain.entities.PermissionInfo;
import com.example.ums.domain.entities.RoleInfo;
import com.example.ums.domain.repositories.PermissionRepository;
import com.example.ums.domain.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionMapper permissionMapper;

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository, RoleRepository roleRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDetails> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PermissionDetails createPermission(PermissionDetails permissionDetails) {
        PermissionInfo permissionInfo = permissionMapper.toEntity(permissionDetails);

        Set<RoleInfo> roles = permissionDetails.getRoles().stream()
                .map(summaryDto -> roleRepository.findById(summaryDto.getRole_id())
                        .orElseThrow(() -> new RuntimeException("Role not found with ID: " + summaryDto.getRole_id())))
                .collect(Collectors.toSet());
        permissionInfo.setRoles(roles);

        PermissionInfo savedEntity = permissionRepository.save(permissionInfo);
        return permissionMapper.toDto(savedEntity);
    }

}

