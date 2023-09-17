package com.tnt.ecommeracemarketplace.service;

import com.tnt.ecommeracemarketplace.dto.*;
import com.tnt.ecommeracemarketplace.entity.UserRoleEnum;
import com.tnt.ecommeracemarketplace.entity.Users;
import com.tnt.ecommeracemarketplace.jwt.JwtUtil;
import com.tnt.ecommeracemarketplace.repository.UserRepository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<ApiResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        Users user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println(password);
            System.out.println(user.getPassword());
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername(), loginRequestDto.getRole()));
        try {
            jwtUtil.addJwtToCookie(response.getHeader(JwtUtil.AUTHORIZATION_HEADER), response);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().body(new ApiResponseDto("로그인에 성공했습니다.", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
        jwtUtil.deleteCookie(response, authResult);
        return ResponseEntity.status(200).body(new ApiResponseDto("로그아웃 성공", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto) {
        if (requestDto.getPassword().contains(requestDto.getUsername())) {
            throw new IllegalArgumentException("비밀번호에 닉네임이 포함되어 있습니다.");
        }
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String address = requestDto.getAddress();
        UserRoleEnum role = UserRoleEnum.USER;

        // 회원 중복 확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 등록
        Users user = Users.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .address(address)
                .role(role)
                .build();
        userRepository.save(user);

        return ResponseEntity.status(201).body(new ApiResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }


    @Override
    public ResponseEntity<ApiResponseDto> signout(Users user, SignoutRequestDto signoutRequestDto, HttpServletResponse response, Authentication authResult) throws ServletException, IOException {

        if (passwordEncoder.matches(signoutRequestDto.getPassword(), user.getPassword())) {
            userRepository.delete(user);
            jwtUtil.deleteCookie(response, authResult);
            return ResponseEntity.status(200).body(new ApiResponseDto("회원탈퇴 성공", HttpStatus.OK.value()));
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public ProfileResponseDto getProfile(Users user) {
        return new ProfileResponseDto(user);
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateProfile(Users user, ProfileRequestDto profileRequestDto) {
        user.setNickname(profileRequestDto.getNickname());
        user.setAddress(profileRequestDto.getAddress());
        userRepository.save(user);
        return ResponseEntity.status(200).body(new ApiResponseDto("프로필 수정 성공", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ApiResponseDto> updatePassword(Users user, PasswordRequestDto passwordRequestDto) {
        if (passwordEncoder.matches(passwordRequestDto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordRequestDto.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.status(200).body(new ApiResponseDto("비밀번호 수정 성공", HttpStatus.OK.value()));
        } else {
            throw new IllegalArgumentException("이전 비밀번호가 일치하지 않습니다.");
        }
    }
}
