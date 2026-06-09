package com.example.ums.application.services;

import com.example.ums.application.dto.RoleDetails;

import java.util.ArrayList;
import java.util.*;

public interface RoleService{
    List<RoleDetails> getAllRoles();
    RoleDetails createRole(RoleDetails roleDetails);
    List<RoleDetails> getBulkRoles(ArrayList<String> roleIds);

    RoleDetails getRoleById(String id);
    RoleDetails updateRoleById(String id, RoleDetails roleDetails);
    void deleteRoleById(String id);
}
