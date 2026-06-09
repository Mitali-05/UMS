package com.example.ums.application.services;

import com.example.ums.application.dto.PermissionDetails;

import java.util.List;

public interface PermissionService {
    List<PermissionDetails> getAllPermissions();
    PermissionDetails createPermission(PermissionDetails permissionDetails);
}
