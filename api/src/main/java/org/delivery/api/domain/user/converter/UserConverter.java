package org.delivery.api.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.user.UserEntity;

import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class UserConverter {
    public UserEntity toEntity(UserRegisterRequest request){
        return Optional.ofNullable(request)
                .map(it->{
                    return UserEntity.builder()
                            .email(request.getEmail())
                            .name(request.getName())
                            .address(request.getAddress())
                            .password(request.getPassword())
                            .build();
                }).orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"UserRegisterRequest Null"));
    }

    public UserEntity toEntity(User user){
        return Optional.ofNullable(user)
                .map(it->{
                    return UserEntity.builder()
                            .email(user.getEmail())
                            .name(user.getName())
                            .status(user.getStatus())
                            .role(user.getRole())
                            .address(user.getAddress())
                            .password(user.getPassword())
                            .build();
                }).orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"User Null"));
    }

    public UserResponse toResponse(UserEntity entity) {
        return Optional.ofNullable(entity)
                .map(it->{
                    return UserResponse.builder()
                            .id(entity.getId())
                            .name(entity.getName())
                            .role(entity.getRole())
                            .email(entity.getEmail())
                            .address(entity.getAddress())
                            .status(entity.getStatus())
                            .registeredAt(entity.getRegisteredAt())
                            .unregisteredAt(entity.getUnregisteredAt())
                            .lastLoginAt(entity.getLastLoginAt())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"UserEntity Null"));
    }
}
