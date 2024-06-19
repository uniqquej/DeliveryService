package org.delivery.storeadmin.domain.userorder.controller;

import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(path = {"","/"})
    public String home(
            @AuthenticationPrincipal UserSession userSession,
            Model model
            ){
        model.addAttribute("storeName", userSession.getStoreName());
        return "home";
    }
}
