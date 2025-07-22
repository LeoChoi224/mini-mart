package com.zerobase.minimart.order.seller.controller;

import com.zerobase.minimart.order.seller.model.ProductInput;
import com.zerobase.minimart.order.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/seller")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/main")
    public String mainPage(Model model) {
        return "order/seller/main";
    }

    @GetMapping("/product_add")
    public String addProductForm() {
        return "order/seller/product_add";
    }

    @PostMapping("/product_add")
    public String addProductSubmit(@ModelAttribute ProductInput parameter,
                                   Model model) {
        sellerService.add(parameter);
        model.addAttribute("message", "상품이 성공적으로 등록되었습니다.");
        return "redirect:/order/seller/product_add";
    }
}