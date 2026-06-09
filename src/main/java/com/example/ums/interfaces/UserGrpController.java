package com.example.ums.interfaces;

import com.example.ums.application.dto.UserGrpDetails;
import com.example.ums.application.services.UserGrpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usergrps")
public class UserGrpController {

    private final UserGrpService userGrpService;

    @Autowired
    public UserGrpController(UserGrpService userGrpService) {
        this.userGrpService = userGrpService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_GROUPS')")
    public ResponseEntity<List<UserGrpDetails>> getAllUserGrps() {
        return ResponseEntity.ok(userGrpService.getAllUserGrps());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_GROUPS')")
    public ResponseEntity<UserGrpDetails> getUserGrpById(@PathVariable String id) {
        return ResponseEntity.ok(userGrpService.getUserGrpById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_GROUPS')")
    public ResponseEntity<UserGrpDetails> createUserGrp(@Valid @RequestBody UserGrpDetails userGrpDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userGrpService.createUserGrp(userGrpDetails));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_GROUPS')")
    public ResponseEntity<UserGrpDetails> updateUserGrp(@PathVariable String id,
                                                        @Valid @RequestBody UserGrpDetails userGrpDetails) {
        return ResponseEntity.ok(userGrpService.updateUserGrpById(id, userGrpDetails));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_GROUPS')")
    public ResponseEntity<Void> deleteUserGrp(@PathVariable String id) {
        userGrpService.deleteUserGrpById(id);
        return ResponseEntity.noContent().build();
    }
}