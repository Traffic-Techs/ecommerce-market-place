package com.tnt.ecommeracemarketplace.service;
import com.tnt.ecommeracemarketplace.dto.*;
import com.tnt.ecommeracemarketplace.entity.Users;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    /**
     * 로그인 API
     * @param loginRequestDto 회원가입 요청 정보
     */
    ResponseEntity<ApiResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response);

    /**
     * 로그아웃 API
     */
    ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException;


    /**
     * 회원가입 API
     * @param requestDto 회원가입 요청 정보
     */
    ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto);


    /**
     * 회원탈퇴 API
     * @param user 로그인한 유저 프로필 정보
     * @param signoutRequestDto 본인확인을 위한 비밀번호 입력
     */
    ResponseEntity<ApiResponseDto> signout(Users user, SignoutRequestDto signoutRequestDto, HttpServletResponse response, Authentication authResult) throws ServletException, IOException;

    ProfileResponseDto getProfile(Users user);

    /**
     * 유저 프로필 수정 API
     * @param user 로그인한 유저 프로필 정보
     * @param profileRequestDto 수정할 유저 프로필 정보
     */
    ResponseEntity<ApiResponseDto> updateProfile(Users user, ProfileRequestDto profileRequestDto);

    /**
     * 비밀번호 수정 API
     * @param user 로그인한 유저 프로필 정보
     * @param passwordRequestDto 이전 비밀번호와 새 비밀번호
     */
    ResponseEntity<ApiResponseDto> updatePassword(Users user, PasswordRequestDto passwordRequestDto);

}
