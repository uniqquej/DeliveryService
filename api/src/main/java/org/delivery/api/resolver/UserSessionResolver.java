package org.delivery.api.resolver;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 지원하는 파라미터 체크, 어노테이션 체크
        var annotation = parameter.hasParameterAnnotation(UserSession.class);
        var parameterType = parameter.getParameterType().equals(User.class);
        return (annotation && parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //support parameter에서 true반환 시 실행됨

        //request context holder에서 찾아오기
        var requestContext = RequestContextHolder.getRequestAttributes();
        var userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        var user = userService.getUserWithThrow((Long) userId);

        //user 정보 세팅
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .role(user.getRole())
                .email(user.getEmail())
                .address(user.getAddress())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
