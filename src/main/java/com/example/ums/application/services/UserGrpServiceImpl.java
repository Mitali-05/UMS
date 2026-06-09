package com.example.ums.application.services;

import com.example.ums.application.dto.UserGrpDetails;
import com.example.ums.application.mapper.UserGrpMapper;
import com.example.ums.domain.entities.RoleInfo;
import com.example.ums.domain.entities.UserGrpInfo;
import com.example.ums.domain.entities.UserInfo;
import com.example.ums.domain.repositories.RoleRepository;
import com.example.ums.domain.repositories.UserGrpRepository;
import com.example.ums.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserGrpServiceImpl implements UserGrpService {
    private final UserGrpRepository userGrpRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserGrpMapper userGrpMapper;

    @Autowired
    public UserGrpServiceImpl(UserGrpRepository userGrpRepository, UserRepository userRepository, RoleRepository roleRepository, UserGrpMapper userGrpMapper) {
        this.userGrpRepository = userGrpRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userGrpMapper = userGrpMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserGrpDetails> getAllUserGrps() {
        return userGrpRepository.findAll().stream()
                .map(userGrpMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserGrpDetails getUserGrpById(String id) {
        Integer grpId = Integer.parseInt(id);
        UserGrpInfo userGrpInfo = userGrpRepository.findById(grpId)
                .orElseThrow(() -> new RuntimeException("User Group not found with ID: " + id));
        return userGrpMapper.toDto(userGrpInfo);
    }

    @Override
    @Transactional
    public UserGrpDetails createUserGrp(UserGrpDetails userGrpDetails) {
        UserGrpInfo userGrpInfo = userGrpMapper.toEntity(userGrpDetails);

        Set<UserInfo> users = userGrpDetails.getUsers().stream()
                .map(summaryDto -> userRepository.findById(summaryDto.getUser_id())
                        .orElseThrow(() -> new RuntimeException("User not found with ID: " + summaryDto.getUser_id())))
                .collect(Collectors.toSet());
        userGrpInfo.setUsers(users);

        Set<RoleInfo> roles = userGrpDetails.getRoles().stream()
                .map(summaryDto -> roleRepository.findById(summaryDto.getRole_id())
                        .orElseThrow(() -> new RuntimeException("Role not found with ID: " + summaryDto.getRole_id())))
                .collect(Collectors.toSet());
        userGrpInfo.setRoles(roles);

        UserGrpInfo savedEntity = userGrpRepository.save(userGrpInfo);
        return userGrpMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    public UserGrpDetails updateUserGrpById(String id, UserGrpDetails userGrpDetails) {
        Integer grpId = Integer.parseInt(id);
        UserGrpInfo existingGrp = userGrpRepository.findById(grpId)
                .orElseThrow(() -> new RuntimeException("User Group not found with ID: " + id));

        existingGrp.setGrp_name(userGrpDetails.getGrp_name());
        existingGrp.setWork_email(userGrpDetails.getWork_email());
        existingGrp.setPhone_no(userGrpDetails.getPhone_no());

        UserGrpInfo updatedEntity = userGrpRepository.save(existingGrp);
        return userGrpMapper.toDto(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteUserGrpById(String id) {
        Integer grpId = Integer.parseInt(id);
        if (!userGrpRepository.existsById(grpId)) {
            throw new RuntimeException("User Group not found with ID: " + id);
        }
        userGrpRepository.deleteById(grpId);
    }
}
