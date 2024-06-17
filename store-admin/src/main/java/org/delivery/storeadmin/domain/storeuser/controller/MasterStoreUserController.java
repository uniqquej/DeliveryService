package org.delivery.storeadmin.domain.storeuser.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.storeuser.business.StoreUserBusiness;
import org.delivery.storeadmin.domain.storeuser.controller.model.StoreUserRegisterRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/master/store-user")
public class MasterStoreUserController {

    private final StoreUserBusiness storeUserBusiness;
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("storeUserRegisterRequest", new StoreUserRegisterRequest());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid StoreUserRegisterRequest request,
            BindingResult bindingResult,
            Model model
    ){
        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            model.addAttribute("errorMessage",errorMessage);
            return "user/register";
        }
        try{
            storeUserBusiness.register(request);

        }catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "user/register";
        }
        return "redirect:/store-user/login";
    }

}
