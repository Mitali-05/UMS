package com.example.ums.domain.repositories;

import com.example.ums.domain.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer>{
    @Query("SELECT u FROM user_info u WHERE u.user_name = :username")
    Optional<UserInfo> findByUserName(@Param("username") String username);
}
