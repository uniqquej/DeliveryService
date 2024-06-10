package org.delivery.storeadmin.domain.storemenu.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.storemenu.business.StoreMenuBusiness;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store-menu")
@RequiredArgsConstructor
public class StoreMenuController {
    private final StoreMenuBusiness storeMenuBusiness;

    @GetMapping("")
    public String getStoreMenuList(
            @AuthenticationPrincipal UserSession userSession,
            Model model
    ){
        var storeMenuList = storeMenuBusiness.getStoreMenuList(userSession.getStoreId());
        model.addAttribute("menuList",storeMenuList);
        return "/store/menu";
    }
}
