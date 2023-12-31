package com.tnt.ecommeracemarketplace.web;

import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
//import com.tnt.ecommeracemarketplace.jwt.JwtUtil;
//import com.tnt.ecommeracemarketplace.repository.UserRepository;
import com.tnt.ecommeracemarketplace.dto.ProfileResponseDto;
import com.tnt.ecommeracemarketplace.jwt.JwtUtil;
import com.tnt.ecommeracemarketplace.repository.UserRepository;
import com.tnt.ecommeracemarketplace.security.UserDetailsImpl;
import com.tnt.ecommeracemarketplace.service.OrderService;
import com.tnt.ecommeracemarketplace.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class WebController {

  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;
  private final ProductService productService;
  private final OrderService orderService;

  private Map<String, Object> cartData = new HashMap<>(); // 임시 데이터 저장용

  @GetMapping("/web")
  public String home() {
    return "index";
  }

  @GetMapping("/web/sign")
  public String signPage() {
    System.out.println("=====Signup Page=====");
    return "sign";
  }

  @GetMapping("/web/user/sign-out")
  public String signoutPage() { return "sign-out"; }

  @GetMapping("/web/products/{productId}")
  public String productDetailsWeb(@PathVariable Long productId, Model model) {
    ProductResponseDto product = productService.findProductDetails(productId);
//    Long findOrders = orderService.findOrders(productId);
    model.addAttribute("product", product);
//    model.addAttribute("order", findOrders);
    return "detailsView";
  }

  @PostMapping("/addToCart")
  @ResponseBody
  public ResponseEntity<String> addToCart(@RequestBody Map<String, Object> requestData) {
    Map<String, Object> product = (Map<String, Object>) requestData.get("product");
    int quantity = (int) requestData.get("quantity");

    // 임시 데이터 저장
    cartData.put("product", product);
    cartData.put("quantity", quantity);

    return ResponseEntity.ok().body("Added to cart successfully");
  }

  @GetMapping("/purchase")
  public String purchasePage(Model model) {
    // 임시 데이터를 purchase.html로 전달
    model.addAttribute("product", cartData.get("product"));
    model.addAttribute("quantity", cartData.get("quantity"));
    return "purchase";
  }

  @GetMapping("/web/mypage")
  public String myPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
    ProfileResponseDto profileResponseDto = new ProfileResponseDto(userDetails.getUser());

    // model 필요한 데이터 담아서 반환
    model.addAttribute("users", profileResponseDto);
    return "my-page";
  }
  @GetMapping("/api/user/profile")
  public String userInfoUpdate() { return "userInfoUpdate"; }

  @GetMapping("/api/user/profile/password")
  public String passwordUpdate() { return "passwordUpdate"; }

}
