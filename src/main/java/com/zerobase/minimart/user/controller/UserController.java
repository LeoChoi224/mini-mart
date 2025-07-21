package com.zerobase.minimart.user.controller;

import com.zerobase.minimart.user.dto.UserInput;
import com.zerobase.minimart.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

//    private final MailService mailService;

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

    @GetMapping("/login")
    public String login(UserInput parameter) {
        return "user/login";
    }

//    @PostMapping("/signup")
//    public String signup(@RequestParam String userId) {
//        // 저장 생략...
//        mailService.sendSignupEmail(userId);
//        return "user/signup_complete";
//    }

//    @GetMapping("/email-auth")
//    public String emailAuth(@RequestParam String id) {
//        Optional<User> optionalUser = userRepository.findByEmailAuthKey(id);
//
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            user.setEmailAuthYn(true);
//            user.setEmailAuthDt(LocalDateTime.now());
//            userRepository.save(user);
//            return "이메일 인증이 완료되었습니다.";
//        }
//
//        return "유효하지 않은 인증 요청입니다.";
//    }

}
