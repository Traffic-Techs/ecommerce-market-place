//package com.tnt.ecommeracemarketplace.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Getter
//@NoArgsConstructor
//@Table(name = "users")
//public class Users {
//
//  @Id
//  @GeneratedValue (strategy = GenerationType.IDENTITY)
//  private Long id;
//
//  @Column(nullable = false, unique = true)
//  private String username;
//
//  @Column(nullable = false)
//  private String password;
//
//  @Column(nullable = false)
//  private String address;
//
//  @Column(nullable = false, unique = true)
//  private String nickname;
//
//  @Column(nullable = false)
//  @Enumerated(value = EnumType.STRING)
//  private UserRoleEnum role;
//
//  @Builder
//  public Users(String username, String password, String address, String nickname, UserRoleEnum role) {
//    this.username = username;
//    this.password = password;
//    this.address = address;
//    this.nickname = nickname;
//    this.role = role;
//  }
//
//}
