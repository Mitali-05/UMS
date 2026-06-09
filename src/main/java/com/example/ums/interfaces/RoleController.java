package com.example.ums.interfaces;

import com.example.ums.application.dto.RoleDetails;
import com.example.ums.application.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ROLES')")
    public ResponseEntity<List<RoleDetails>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ROLES')")
    public ResponseEntity<RoleDetails> getRoleById(@PathVariable String id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROLES')")
    public ResponseEntity<RoleDetails> createRole(@Valid @RequestBody RoleDetails roleDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(roleDetails));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ROLES')")
    public ResponseEntity<RoleDetails> updateRole(@PathVariable String id,
                                                  @Valid @RequestBody RoleDetails roleDetails) {
        return ResponseEntity.ok(roleService.updateRoleById(id, roleDetails));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ROLES')")
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        roleService.deleteRoleById(id);
        return ResponseEntity.noContent().build();
    }
}