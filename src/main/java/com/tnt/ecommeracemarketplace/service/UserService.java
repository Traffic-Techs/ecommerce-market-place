//package com.tnt.ecommeracemarketplace.service;
//import com.tnt.ecommeracemarketplace.dto.ApiResponseDto;
//import com.tnt.ecommeracemarketplace.dto.LoginRequestDto;
//import com.tnt.ecommeracemarketplace.dto.SignoutRequestDto;
//import com.tnt.ecommeracemarketplace.dto.SignupRequestDto;
//import com.tnt.ecommeracemarketplace.entity.Users;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//
//public interface UserService {
//    /**
//     * 로그인 API
//     * @param loginRequestDto 회원가입 요청 정보
//     */
//    ResponseEntity<ApiResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response);
//
//    /**
//     * 로그아웃 API
//     */
//    ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException;
//
//
//    /**
//     * 회원가입 API
//     * @param requestDto 회원가입 요청 정보
//     */
//    ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto);
//
//
//    /**
//     * 회원탈퇴 API
//     * @param user 로그인한 유저 프로필 정보
//     * @param signoutRequestDto 본인확인을 위한 비밀번호 입력
//     */
//    ResponseEntity<ApiResponseDto> signout(Users user, SignoutRequestDto signoutRequestDto, HttpServletResponse response, Authentication authResult) throws ServletException, IOException;
//}
