package org.delivery.storeadmin.domain.storeuser.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.delivery.storeadmin.domain.storeuser.controller.model.StoreUserRegisterRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store-user")
public class StoreUserApi {

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("errorMessage", "아이디 또는 비밀번호를 확인해주세요.");
        return "user/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("storeUserRegisterRequest", new StoreUserRegisterRequest());
        return "user/register";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/store-user/login";
    }
    @GetMapping("/error/forbidden")
    public String error(){
        return "error/forbidden";
    }
}
