package org.delivery.api.domain.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.controller.model.UserUpdateRequest;
import org.delivery.api.domain.user.model.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me(@UserSession User user){
        var response = userBusiness.me(user);
        return Api.OK(response);
    }

    @PostMapping("/id")
    public Api<UserResponse> register(
            @RequestBody @Valid Api<UserUpdateRequest> request,
            BindingResult bindingResult,
            @Parameter(hidden = true) @UserSession User user
    ){
        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ApiException(ErrorCode.BAD_REQUEST, errorMessage);
        }
        var response = userBusiness.update(request.getBody(),user);
        return Api.OK(response);
    }
}
