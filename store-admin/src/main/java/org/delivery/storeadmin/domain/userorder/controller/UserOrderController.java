package org.delivery.storeadmin.domain.userorder.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store-order")
public class UserOrderController {
    @GetMapping(path = "/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public String orderInfo(){
        return "home";
    }
}
