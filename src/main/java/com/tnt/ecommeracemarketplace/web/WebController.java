package com.tnt.ecommeracemarketplace.web;

import com.tnt.ecommeracemarketplace.dto.ProductResponseDto;
import com.tnt.ecommeracemarketplace.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class WebController {

  private final ProductService productService;

  @GetMapping("/web")
  public String home() {
    return "index";
  }

  @GetMapping("/web/products/{productId}")
  public String productDetailsWeb(@PathVariable Long productId, Model model) {
    ProductResponseDto product = productService.findProductDetails(productId);
    model.addAttribute("product", product);
    return "detailsView"; // 뷰 이름 반환
  }
}
