package com.zerobase.minimart.order.seller.controller;

import com.zerobase.minimart.order.seller.model.ProductInput;
import com.zerobase.minimart.order.seller.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/seller")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/main")
    public String mainPage(Model model) {
        return "order/seller/main";
    }

    @GetMapping("/product/add")
    public String addProductForm() {
        return "order/seller/add";
    }

    @PostMapping("/product/add")
    public String addProductSubmit(@ModelAttribute ProductInput parameter,
                                   Model model) {
        productService.add(parameter);
        model.addAttribute("message", "상품이 성공적으로 등록되었습니다.");
        return "redirect:/order/seller/product/add";
    }
}