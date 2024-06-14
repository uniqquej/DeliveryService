package org.delivery.api.domain.likestore.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.likestore.business.LikeStoreBusiness;
import org.delivery.api.domain.likestore.controller.model.LikeStoreResponse;
import org.delivery.api.domain.user.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like-store")
public class LikeStoreApiController {

    private final LikeStoreBusiness likeStoreBusiness;
    @PostMapping("/id/{id}")
    public Api<LikeStoreResponse>  likeStore(
            @PathVariable(name = "id") Long storeId,
             @UserSession User user
    ){
            var response = likeStoreBusiness.likeStore(storeId,user);
            return Api.OK(response);
    }


}
