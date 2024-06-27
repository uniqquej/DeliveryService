package org.delivery.storeadmin.domain.storemenu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.storemenu.business.StoreMenuBusiness;
import org.delivery.storeadmin.domain.storemenu.controller.model.MenuRegisterRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

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
        return "store/menu";
    }

    @GetMapping("/register")
    public String registerMenuForm(Model model){
        model.addAttribute("menuRegisterRequest", new MenuRegisterRequest());
        return "store/menuRegister";
    }

    @PostMapping("/register")
    public String registerMenu(
            @Valid @ModelAttribute MenuRegisterRequest menuRegisterRequest,
            @RequestParam(name = "menuImgFile")MultipartFile menuImgFile,
            @AuthenticationPrincipal UserSession userSession,
            BindingResult bindingResult,
            Model model
    ){
        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            model.addAttribute("errorMessage",errorMessage);
            return "store/menuRegister";
        }

        try {
            storeMenuBusiness.registerMenu(userSession.getStoreId(), menuRegisterRequest, menuImgFile);
        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "store/menuRegister";
        }
        return "redirect:/store-menu";
    }
}
