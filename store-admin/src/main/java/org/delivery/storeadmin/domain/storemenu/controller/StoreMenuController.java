package org.delivery.storeadmin.domain.storemenu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.storeadmin.domain.storemenu.controller.model.MenuRegisterRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/register")
    public String registerMenuForm(Model model){
        model.addAttribute("menuRegisterRequest", new MenuRegisterRequest());
        return "store/menuRegister";
    }

    @PostMapping("/register")
    public String registerMenu(
            @Valid MenuRegisterRequest menuRegisterRequest,
            @RequestParam(name = "menuImgFile")MultipartFile menuImgFile,
            @AuthenticationPrincipal UserSession userSession,
            Model model
    ){
        try {
            storeMenuBusiness.registerMenu(userSession.getStoreId(), menuRegisterRequest, menuImgFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/store-menu";
    }
}
