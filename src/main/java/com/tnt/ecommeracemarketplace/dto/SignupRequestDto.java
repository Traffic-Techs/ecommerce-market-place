package com.tnt.ecommeracemarketplace.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{3,10}$", message = "최소 3자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9) 로 구성되어야 합니다.")
    private String username;

    @Pattern(regexp = "^[a-z0-9]{3,10}$", message = "최소 3자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9) 로 구성되어야 합니다.")
    private String nickname;

    // (?=.*[a-zA-Z])(?=.*\d)
    @Pattern(regexp = "^[a-zA-Z0-9#?!@$%^&*-]{8,15}$", message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9) 로 구성되어야 합니다.")
    private String password;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String address;
}