package com.zerobase.minimart.order.seller.controller;

import com.zerobase.minimart.order.seller.entity.Product;
import com.zerobase.minimart.order.seller.model.ProductInput;
import com.zerobase.minimart.order.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

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
                                   Principal principal,
                                   RedirectAttributes redirectAttributes
    ) {
        String userId = principal.getName();

        sellerService.add(parameter, userId);

        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 등록되었습니다.");
        return "redirect:/order/seller/my_product_list";
    }

    @GetMapping("/my_product_list")
    public String myProducts(Model model, Principal principal,
                             @ModelAttribute("message") String message) {

        String userId = principal.getName();  // 로그인된 사용자의 이메일
        List<Product> productList = sellerService.getProductsByUser(userId);

        model.addAttribute("products", productList);
        model.addAttribute("message", message);

        return "order/seller/my_product_list";
    }
}