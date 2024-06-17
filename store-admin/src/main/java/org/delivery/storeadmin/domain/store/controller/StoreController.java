package org.delivery.storeadmin.domain.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.store.business.StoreBusiness;
import org.delivery.storeadmin.domain.store.controller.model.StoreRegisterRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/master/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreBusiness storeBusiness;

    @GetMapping("/register")
    public String store(Model model){
        model.addAttribute("storeRegisterRequest", new StoreRegisterRequest());
        return "store/storeRegister";
    }

    @PostMapping("/register")
    public String registerMenu(
            @Valid StoreRegisterRequest storeRegisterRequest,
            @RequestParam(name = "storeImgFile") MultipartFile storeImgFile,
            BindingResult bindingResult,
            Model model
    ){
        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            model.addAttribute("errorMessage",errorMessage);
            return "redirect:/master/store/register";
        }

        try {
            storeBusiness.registerStore(storeRegisterRequest, storeImgFile);
        } catch (Exception e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "redirect:/master/store/register";
        }
        return "redirect:/";
    }
}
