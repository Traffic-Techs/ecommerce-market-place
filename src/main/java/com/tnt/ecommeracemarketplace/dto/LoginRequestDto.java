package com.tnt.ecommeracemarketplace.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tnt.ecommeracemarketplace.entity.UserRoleEnum;
import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
    @JsonIgnore
    private UserRoleEnum role;
}
