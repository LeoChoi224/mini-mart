package com.zerobase.minimart.order.seller.controller;

import com.zerobase.minimart.config.UserPrincipal;
import com.zerobase.minimart.order.common.model.ProductSearchInput;
import com.zerobase.minimart.order.common.service.CommonService;
import com.zerobase.minimart.order.customer.service.CartService;
import com.zerobase.minimart.order.entity.Product;
import com.zerobase.minimart.order.seller.model.ProductInput;
import com.zerobase.minimart.order.seller.service.SellerService;
import com.zerobase.minimart.user.dto.UserDto;
import com.zerobase.minimart.user.entity.User;
import com.zerobase.minimart.user.model.ResetPasswordInput;
import com.zerobase.minimart.user.model.UserInput;
import com.zerobase.minimart.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final UserService userService;
    private final CommonService commonService;

    @GetMapping("/info")
    public String SellerInfo(Model model,
                             @AuthenticationPrincipal UserPrincipal userPrincipal) {

        String userId = userPrincipal.getUsername(); // 또는 userPrincipal.getUser().getUserId()
        UserDto user = userService.info(userId);

        model.addAttribute("user", user);
        return "order/seller/info";
    }

    @PostMapping("/update")
    public String updateSellerInfo(@ModelAttribute UserInput userInput) {
        userService.updateUserInfo(userInput);
        return "redirect:/order/seller/info"; // 또는 성공 페이지
    }

    @PostMapping("/updateField")
    public String updateUserField(@RequestParam String userId,
                                  @RequestParam String field,
                                  @RequestParam String value,
                                  RedirectAttributes redirectAttributes) {

        if ("password".equals(field)) {
            userService.updatePassword(userId, value);
            redirectAttributes.addAttribute("message", "비밀번호가 변경되었습니다.");
        } else if ("phoneNumber".equals(field)) {
            userService.updatePhoneNumber(userId, value);
            redirectAttributes.addAttribute("message", "전화번호가 변경되었습니다.");
        }

        return "redirect:/order/seller/info";
    }


    @GetMapping("/list")
    public String list(@ModelAttribute ProductSearchInput input, Model model) {
        List<Product> products = commonService.searchProducts(input);
        model.addAttribute("products", products);
        model.addAttribute("input", input);

        return "order/seller/product_list";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam Long id, Model model) {
        Product product = commonService.getProductDetail(id);
        model.addAttribute("product", product);
        return "order/seller/product_detail";
    }

    @GetMapping("/product/add")
    public String addProductForm() {

        return "order/seller/product_add";
    }

    @PostMapping("/product/add")
    public String addProductSubmit(@ModelAttribute ProductInput parameter,
                                   Principal principal,
                                   RedirectAttributes redirectAttributes
    ) {
        String userId = principal.getName();

        sellerService.add(parameter, userId);

        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 등록되었습니다.");
        return "redirect:/order/seller/product/my_list";
    }

    @GetMapping("/product/my_list")
    public String myProducts(Model model, Principal principal,
                             @ModelAttribute("message") String message) {

        String userId = principal.getName();  // 로그인된 사용자의 이메일
        List<Product> productList = sellerService.getProductsByUser(userId);

        model.addAttribute("products", productList);

        return "order/seller/my_product_list";
    }

    @PostMapping("/product/status")
    public String updateStatus(@RequestParam Long id,
                               @RequestParam String status,
                               RedirectAttributes redirectAttributes) {
        sellerService.updateStatus(id, status);
        redirectAttributes.addFlashAttribute("message", "상태가 변경되었습니다.");
        return "redirect:/order/seller/product/my_list";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable  Long id,
                                RedirectAttributes redirectAttributes) {
        sellerService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("message", "상품이 삭제되었습니다.");
        return "redirect:/order/seller/product/my_list";
    }

    @GetMapping("/product/update/{id}")
    public String detailPage(@PathVariable  Long id, Model model) {
        Product product = sellerService.getProduct(id);
        model.addAttribute("product", product);
        return "order/seller/product_update";
    }

    @PostMapping("/product/update/{id}")
    public String updateProduct(@PathVariable  Long id,
                                @ModelAttribute ProductInput parameter,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {
        String userId = principal.getName();

        // URL의 ID와 form 데이터의 ID가 다를 경우 (수정 조작, 오작동 등 방지)
        if (!id.equals(parameter.getId())) {
            // 해킹, 실수 등으로 의심되는 상황 → 처리 중단
            redirectAttributes.addFlashAttribute("message", "잘못된 접근입니다.");
            return "redirect:/order/seller/product/my_list";
        }

        boolean result = sellerService.update(parameter, userId);

        if (result) {
            redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 수정되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "상품 수정에 실패했습니다.");
        }

        return "redirect:/order/seller/product/update/" + id;
    }


}