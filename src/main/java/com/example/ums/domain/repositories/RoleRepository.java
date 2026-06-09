package com.example.ums.domain.repositories;

import com.example.ums.domain.entities.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleInfo, Integer>{
}