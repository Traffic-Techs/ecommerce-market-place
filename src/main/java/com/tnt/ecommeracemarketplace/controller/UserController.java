//package com.tnt.ecommeracemarketplace.controller;
//
//import com.tnt.ecommeracemarketplace.dto.ApiResponseDto;
//import com.tnt.ecommeracemarketplace.dto.LoginRequestDto;
//import com.tnt.ecommeracemarketplace.dto.SignoutRequestDto;
//import com.tnt.ecommeracemarketplace.dto.SignupRequestDto;
//import com.tnt.ecommeracemarketplace.security.UserDetailsImpl;
//import com.tnt.ecommeracemarketplace.service.UserServiceImpl;
//import jakarta.annotation.Nullable;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.Valid;
//import java.io.IOException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/user")
//public class UserController {
//
//    private final UserServiceImpl userServiceImpl;
//
//    @PostMapping("/login")
//    public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
//        return userServiceImpl.login(loginRequestDto, response);
//    }
//
//    @DeleteMapping("/logout")
//    public ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
//
//        return userServiceImpl.logout(response, authResult);
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
//        return userServiceImpl.signup(requestDto);
//    }
//
//    @Transactional
//    @DeleteMapping("/signout")
//    public ResponseEntity<ApiResponseDto> signout(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody SignoutRequestDto signoutRequestDto,
//                                                  HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
//        return userServiceImpl.signout(userDetails.getUser(), signoutRequestDto, response, authResult);
//    }
//}
