package org.delivery.api.domain.review.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.review.business.ReviewBusiness;
import org.delivery.api.domain.review.controller.model.ReviewRegisterRequest;
import org.delivery.api.domain.review.controller.model.ReviewResponse;
import org.delivery.api.domain.review.controller.model.ReviewUpdateRequest;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.business.UserOrderBusiness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewBusiness reviewBusiness;
    private final UserOrderBusiness userOrderBusiness;

    @PostMapping("/register")
    public Api<ReviewResponse> register(
            @Valid @RequestBody Api<ReviewRegisterRequest> request,
            @Parameter(hidden = true) @UserSession User user
    ){
        var response = reviewBusiness.register(request.getBody(),user);
        return Api.OK(response);
    }

    @GetMapping("/search")
    public Api<Page<ReviewResponse>> search(
            @RequestParam(name = "storeId",required = false) Long storeId,
            @Parameter(hidden = true) @UserSession User user,
            @PageableDefault(page=1) Pageable pageable
    ){
        if(storeId==null) {
            var response = reviewBusiness.searchByUserId(user.getId(),pageable);
            return Api.OK(response);
        }
        var response = reviewBusiness.searchByStoreId(storeId,pageable);
        return Api.OK(response);
    }

    @GetMapping("/id/{id}")
    public Api<ReviewResponse> getReview(
            @PathVariable Long id
    ){
        var response = reviewBusiness.getReview(id);
        return Api.OK(response);
    }

    @PostMapping("/id/{id}")
    public Api<ReviewResponse> updateReview(
            @PathVariable Long id,
            @RequestBody Api<ReviewUpdateRequest> request,
            @Parameter(hidden = true) @UserSession User user
            ){
        var response = reviewBusiness.updateReview(id, request.getBody(), user);
        return Api.OK(response);
    }

}
