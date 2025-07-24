package com.zerobase.minimart.user.controller;

import com.zerobase.minimart.config.UserPrincipal;
import com.zerobase.minimart.exception.CustomException;
import com.zerobase.minimart.user.dto.UserDto;
import com.zerobase.minimart.user.model.ResetPasswordInput;
import com.zerobase.minimart.user.model.UserInput;
import com.zerobase.minimart.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signUpSubmit(UserInput parameter, RedirectAttributes redirectAttributes) {
        boolean result = userService.signUp(parameter);

        if (result) {
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return "redirect:/user/login";
        } else {
            redirectAttributes.addFlashAttribute("signupSuccess", false);
            return "redirect:/user/signup";
        }
    }

    @GetMapping("/email_auth")
    public String emailAuth(Model model, HttpServletRequest request) {

        String uuid = request.getParameter("uuid");
        System.out.println(uuid);

        boolean result = userService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "user/email_auth";
    }

    @GetMapping("/reset_password")
    public String resetPassword(Model model, @RequestParam("id") String uuid) {

        boolean result = userService.checkResetPassword(uuid);

        model.addAttribute("result", result);
        model.addAttribute("uuid", uuid);

        return "user/reset_password";
    }

    @PostMapping("/reset_password")
    public String resetPasswordSubmit(Model model,
                                      @ModelAttribute ResetPasswordInput input) {

        if (!input.getPassword().equals(input.getRePassword())) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("uuid", input.getUuid());
            return "user/reset_password";
        }

        boolean result = userService.resetPassword(input.getUuid(), input.getPassword());
        model.addAttribute("result", result);

        return "user/reset_password_result";
    }

    @PostMapping("/applySeller")
    public String applySeller(@RequestParam String userId) {
        try {
            userService.applySeller(userId);
            return "redirect:/order/customer/info?message=" + UriUtils.encode("판매자 신청이 완료되었습니다. \n 재 로그인 시 판매자 계정으로 이용 가능합니다.", StandardCharsets.UTF_8);
        } catch (CustomException e) {
            return "redirect:/order/customer/info?message=" + UriUtils.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
    }

    @GetMapping("/find_password")
    public String findPassword() {
        return "user/find_password";
    }

    @PostMapping("/find_password")
    public String findPasswordSubmit(Model model, ResetPasswordInput parameter) {

        boolean result = false;
        try {
            result = userService.sendResetPassword(parameter);
        } catch (Exception e) {
        }
        model.addAttribute("result", result);

        return "user/find_password_result";
    }
}
