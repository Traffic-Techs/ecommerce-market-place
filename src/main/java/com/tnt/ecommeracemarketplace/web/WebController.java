package com.tnt.ecommeracemarketplace.web;

import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.service.OrderService;
import com.tnt.ecommeracemarketplace.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class WebController {

  private final ProductService productService;
  private final OrderService orderService;

  private Map<String, Object> cartData = new HashMap<>(); // 임시 데이터 저장용

  @GetMapping("/web")
  public String home() {
    return "index";
  }

  @GetMapping("/web/products/{productId}")
  public String productDetailsWeb(@PathVariable Long productId, Model model) {
    ProductResponseDto product = productService.findProductDetails(productId);
    Integer findOrders = orderService.findOrders(productId);
    model.addAttribute("product", product);
    model.addAttribute("order", findOrders);
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

}
