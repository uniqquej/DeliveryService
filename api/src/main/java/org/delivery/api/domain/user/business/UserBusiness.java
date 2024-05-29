package org.delivery.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.service.UserService;

@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;

    public UserResponse register(UserRegisterRequest request) {
        var entity = userConverter.toEntity(request);
        var savedEntity = userService.register(entity);
        var response = userConverter.toResponse(savedEntity);
        return response;
    }

    public UserResponse login(UserLoginRequest request) {
        var user = userService.login(request);

        // 토큰 생성 후 전달

        return userConverter.toResponse(user);
    }
}
