package org.delivery.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.token.converter.TokenConverter;
import org.delivery.api.domain.token.service.TokenService;
import org.delivery.db.user.UserEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TokenBusiness {
    private final TokenConverter tokenConverter;
    private final TokenService tokenService;

    public TokenResponse issueToken(UserEntity user){
        return Optional.ofNullable(user)
                .map(it->{
                    var userId = user.getId();
                    var accessToken = tokenService.issueAccessToken(userId);
                    var refreshToken = tokenService.issueRefreshToken(userId);

                    return tokenConverter.toResponnse(accessToken,refreshToken);

                })
                .orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }
}
