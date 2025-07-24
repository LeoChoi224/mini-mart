package com.zerobase.minimart.order.customer.controller;

import com.zerobase.minimart.config.UserPrincipal;
import com.zerobase.minimart.exception.CustomException;
import com.zerobase.minimart.order.common.model.ProductSearchInput;
import com.zerobase.minimart.order.common.service.CommonService;
import com.zerobase.minimart.order.customer.service.CartService;
import com.zerobase.minimart.order.entity.Product;
import com.zerobase.minimart.user.dto.UserDto;
import com.zerobase.minimart.user.entity.User;
import com.zerobase.minimart.user.model.UserInput;
import com.zerobase.minimart.user.service.UserService;
import com.zerobase.minimart.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/customer")
public class CustomerController {

    private final UserUtil userUtil;
    private final CartService cartService;
    private final UserService userService;
    private final CommonService commonService;

    @GetMapping("/info")
    public String CustomerInfo(Model model,
                             @AuthenticationPrincipal UserPrincipal userPrincipal) {

        String userId = userPrincipal.getUsername(); // 또는 userPrincipal.getUser().getUserId()
        UserDto user = userService.info(userId);

        model.addAttribute("user", user);
        return "order/customer/info";
    }

    @PostMapping("/update")
    public String updateCustomerInfo(@ModelAttribute UserInput userInput) {
        userService.updateUserInfo(userInput);
        return "redirect:/order/customer/info"; // 또는 성공 페이지
    }

    @PostMapping("/updateField")
    public String updateCustomerField(@RequestParam String userId,
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

        return "redirect:/order/customer/info";
    }

    @GetMapping("/list")
    public String list(@ModelAttribute ProductSearchInput input, Model model) {
        List<Product> products = commonService.searchProducts(input);
        model.addAttribute("products", products);
        model.addAttribute("input", input);
        return "order/customer/product_list";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam Long id, Model model) {
        Product product = commonService.getProductDetail(id);
        model.addAttribute("product", product);
        return "order/customer/product_detail";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        User user = userUtil.getLoginUser();
        model.addAttribute("cartItems", cartService.getCartItems(user));
        return "order/customer/cart_list";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1") int quantity) {
        User user = userUtil.getLoginUser();
        cartService.addToCart(user, productId, quantity);
        return "redirect:/order/customer/cart_list";
    }

    @PostMapping("/cart/update")
    public String updateQuantity(@RequestParam Long cartItemId,
                                 @RequestParam int quantity) {
        User user = userUtil.getLoginUser();
        cartService.updateQuantity(user, cartItemId, quantity);
        return "redirect:/order/customer/cart_list";
    }

    @PostMapping("/cart/delete")
    public String deleteItem(@RequestParam Long cartItemId) {
        User user = userUtil.getLoginUser();
        cartService.removeFromCart(user, cartItemId);
        return "redirect:/order/customer/cart_list";
    }

    @GetMapping("/history")
    public String orderHistory(Model model) {
        User user = userUtil.getLoginUser();
        model.addAttribute("cartItems", cartService.getCartItems(user));
        return "order/customer/order_history";
    }


}
