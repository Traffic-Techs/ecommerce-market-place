package com.tnt.ecommeracemarketplace.controller;

import com.tnt.ecommeracemarketplace.dto.*;
import com.tnt.ecommeracemarketplace.security.UserDetailsImpl;
import com.tnt.ecommeracemarketplace.service.UserServiceImpl;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userServiceImpl.login(loginRequestDto, response);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException {

        return userServiceImpl.logout(response, authResult);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return userServiceImpl.signup(requestDto);
    }

    @Transactional
    @DeleteMapping("/signout")
    public ResponseEntity<ApiResponseDto> signout(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody SignoutRequestDto signoutRequestDto,
                                                  HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
        return userServiceImpl.signout(userDetails.getUser(), signoutRequestDto, response, authResult);
    }

    @GetMapping("/info")
    public ProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userServiceImpl.getProfile(userDetails.getUser());
    }

    @Transactional
    @PutMapping("/info")
    public ResponseEntity<ApiResponseDto> updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto profileRequestDto) {
        return userServiceImpl.updateProfile(userDetails.getUser(), profileRequestDto);
    }

    @Transactional
    @PutMapping("/info/password")
    public ResponseEntity<ApiResponseDto> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody PasswordRequestDto passwordRequestDto) {
        return userServiceImpl.updatePassword(userDetails.getUser(), passwordRequestDto);
    }
}
