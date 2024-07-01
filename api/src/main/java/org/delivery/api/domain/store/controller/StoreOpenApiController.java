package org.delivery.api.domain.store.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.store.business.StoreBusiness;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.store.enums.StoreCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/open-api/store")
@RequiredArgsConstructor
public class StoreOpenApiController {
    private final StoreBusiness storeBusiness;

    @GetMapping("/search")
    public Api<Slice<StoreResponse>> search(
            @RequestParam(name="category", required = false)
            StoreCategory category,
            @RequestParam(name="id", required = false)
            Long lastId,
            @RequestParam(name = "region", required = false)
            String region,
            @RequestParam( name = "name", required = false)
            String name,
            Pageable pageable
    ){
        if ("전체".equals(region)) {
            region = null;
        }
        var response = storeBusiness.search(category, pageable, lastId, region, name);
        return Api.OK(response);
    }
}
