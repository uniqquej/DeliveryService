package org.delivery.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.controller.model.UserUpdateRequest;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;

@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;

    private final TokenBusiness tokenBusiness;

    public UserResponse register(UserRegisterRequest request) {
        var entity = userConverter.toEntity(request);
        UserResponse response;
        try{
            var savedEntity = userService.register(entity);
            response = userConverter.toResponse(savedEntity);
        }catch(ApiException e){
            response = UserResponse.builder()
                    .name(request.getName())
                    .email("이미 존재하는 이메일입니다.")
                    .address(request.getAddress())
                    .build();
        }
        return response;
    }

    public TokenResponse login(UserLoginRequest request) {
        var user = userService.login(request);

        var tokenResponse = tokenBusiness.issueToken(user);
        return tokenResponse;
    }

    public UserResponse me(User user) {
        var userEntity = userService.getUserWithThrow(user.getId());
        var response = userConverter.toResponse(userEntity);
        return response;
    }

    public UserResponse update(UserUpdateRequest request, User user) {
        var userEntity = userService.update(user.getId(),request);
        var response = userConverter.toResponse(userEntity);
        return response;
    }
}
