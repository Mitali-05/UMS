package com.example.ums.domain.repositories;

import com.example.ums.domain.entities.UserGrpInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGrpRepository extends JpaRepository<UserGrpInfo, Integer>{
}
