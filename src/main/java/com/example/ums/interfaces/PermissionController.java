package com.example.ums.interfaces;

import com.example.ums.application.dto.PermissionDetails;
import com.example.ums.application.services.PermissionService;
import com.example.ums.application.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_PERMISSIONS')")
    public ResponseEntity<List<PermissionDetails>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PERMISSIONS')")
    public ResponseEntity<PermissionDetails> createPermission(@Valid @RequestBody PermissionDetails permissionDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.createPermission(permissionDetails));
    }
}