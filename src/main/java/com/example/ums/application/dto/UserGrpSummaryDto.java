package com.example.ums.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserGrpSummaryDto {
    Integer grp_id;
    String grp_name;
    String work_email;
    String phone_no;
}
