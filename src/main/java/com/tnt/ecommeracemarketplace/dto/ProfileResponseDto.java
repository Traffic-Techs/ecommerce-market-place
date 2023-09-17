package com.tnt.ecommeracemarketplace.dto;

import com.tnt.ecommeracemarketplace.entity.Users;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String username;
    private String nickname;
    private String address;

    public ProfileResponseDto(Users user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.address = user.getAddress();
    }
}
