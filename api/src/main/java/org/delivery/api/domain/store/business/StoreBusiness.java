package org.delivery.api.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.db.store.enums.StoreCategory;

import java.util.List;

@RequiredArgsConstructor
@Business
public class StoreBusiness {
    private final StoreService storeService;
    private final StoreConverter storeConverter;

    public StoreResponse register(StoreRegisterRequest request){
        var entity = storeConverter.toEntity(request);
        var savedEntity = storeService.register(entity);
        var response = storeConverter.toResponse(savedEntity);
        return response;
    }

    public List<StoreResponse> searchCategory(StoreCategory category){
        var entityList = storeService.searchByCategory(category);
        var response = entityList.stream().map(storeConverter::toResponse);
        return response.toList();
    }
}
