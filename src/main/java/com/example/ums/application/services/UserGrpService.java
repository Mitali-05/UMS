package com.example.ums.application.services;

import com.example.ums.application.dto.UserGrpDetails;
import java.util.List;

public interface UserGrpService {
    List<UserGrpDetails> getAllUserGrps();
    UserGrpDetails createUserGrp(UserGrpDetails userGrpDetails);
    UserGrpDetails getUserGrpById(String id);
    UserGrpDetails updateUserGrpById(String id,UserGrpDetails userGrpDetails);
    void deleteUserGrpById(String Id);
}
