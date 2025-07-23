package com.zerobase.minimart.order.common.controller;

import com.zerobase.minimart.order.common.model.ProductSearchInput;
import com.zerobase.minimart.order.common.service.CommonService;
import com.zerobase.minimart.order.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/common")
public class CommonController {

    private final CommonService commonService;

    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("input", new ProductSearchInput());
        return "order/common/search_main_page";
    }

    @GetMapping("/list")
    public String list(@ModelAttribute ProductSearchInput input, Model model) {
        List<Product> products = commonService.searchProducts(input);
        model.addAttribute("products", products);
        model.addAttribute("input", input);
        return "order/common/product_list";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam Long id, Model model) {
        Product product = commonService.getProductDetail(id);
        model.addAttribute("product", product);
        return "order/common/product_detail";
    }

}