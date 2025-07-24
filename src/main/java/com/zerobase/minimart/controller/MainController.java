package com.zerobase.minimart.controller;

import com.zerobase.minimart.config.UserPrincipal;
import com.zerobase.minimart.order.common.model.ProductSearchInput;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

    @RequestMapping("/")
    public String index() { return "index"; }

    @GetMapping("/order/common/home")
    public String search(Model model) {
        model.addAttribute("input", new ProductSearchInput());
        return "order/common/home";
    }

    @GetMapping("/order/customer/home")
    public String customerHome(Model model){
        model.addAttribute("input", new ProductSearchInput());
        return "order/customer/home";
    }

    @GetMapping("/order/seller/home")
    public String sellerHome(Model model){
        model.addAttribute("input", new ProductSearchInput());
        return "order/seller/home";
    }

    @RequestMapping("error/denied")
    public String errorDenied() {
        return "error/denied";
    }

}
