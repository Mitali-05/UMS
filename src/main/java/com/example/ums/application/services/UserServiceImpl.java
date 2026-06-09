package com.example.ums.application.services;

import com.example.ums.application.dto.UserDetails;
import com.example.ums.application.mapper.UserMapper;
import com.example.ums.domain.entities.RoleInfo;
import com.example.ums.domain.entities.UserGrpInfo;
import com.example.ums.domain.entities.UserInfo;
import com.example.ums.domain.repositories.RoleRepository;
import com.example.ums.domain.repositories.UserGrpRepository;
import com.example.ums.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserGrpRepository userGrpRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,UserGrpRepository userGrpRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userGrpRepository = userGrpRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDetails> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails getUserById(String id) {
        Integer userId = Integer.parseInt(id);
        UserInfo userInfo = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return userMapper.toDto(userInfo);
    }

    @Override
    @Transactional
    public UserDetails createUser(UserDetails userDetails) {
        UserInfo userInfo = userMapper.toEntity(userDetails);
        userInfo.setPassword(passwordEncoder.encode(userDetails.getPassword()));

        Set<RoleInfo> roles = userDetails.getRoles().stream()
                .map(summaryDto -> roleRepository.findById(summaryDto.getRole_id())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + summaryDto.getRole_name())))
                .collect(Collectors.toSet());
        userInfo.setRoles(roles);

        Set<UserGrpInfo> grps = userDetails.getGrps().stream()
                .map(summaryDto -> userGrpRepository.findById(summaryDto.getGrp_id())
                        .orElseThrow(() -> new RuntimeException("Group not found: " + summaryDto.getGrp_name())))
                .collect(Collectors.toSet());
        userInfo.setGrps(grps);

        UserInfo savedEntity = userRepository.save(userInfo);
        return userMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    public UserDetails updateUserById(String id, UserDetails userDetails) {
        Integer userId = Integer.parseInt(id);

        UserInfo existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        existingUser.setUser_name(userDetails.getUser_name());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhone_no(userDetails.getPhone_no());

        UserInfo updatedEntity = userRepository.save(existingUser);
        return userMapper.toDto(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteUserById(String id) {
        Integer userId = Integer.parseInt(id);
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(userId);
    }
}
