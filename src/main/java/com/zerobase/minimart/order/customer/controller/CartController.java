package com.zerobase.minimart.order.customer.controller;

import com.zerobase.minimart.order.customer.service.CartService;
import com.zerobase.minimart.user.entity.User;
import com.zerobase.minimart.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order/customer/cart")
public class CartController {

    private final UserUtil userUtil;
    private final CartService cartService;

    @GetMapping
    public String viewCart(Model model) {
        User user = userUtil.getLoginUser();
        model.addAttribute("cartItems", cartService.getCartItems(user));
        return "order/customer/cart_list";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1") int quantity) {
        User user = userUtil.getLoginUser();
        cartService.addToCart(user, productId, quantity);
        return "redirect:/order/customer/cart_list";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long cartItemId,
                                 @RequestParam int quantity) {
        User user = userUtil.getLoginUser();
        cartService.updateQuantity(user, cartItemId, quantity);
        return "redirect:/order/customer/cart_list";
    }

    @PostMapping("/delete")
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
