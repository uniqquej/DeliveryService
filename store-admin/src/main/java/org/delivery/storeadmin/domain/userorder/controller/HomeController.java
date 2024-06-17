package org.delivery.storeadmin.domain.userorder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(path = {"","/"})
    public String home(){
        return "home";
    }
}
