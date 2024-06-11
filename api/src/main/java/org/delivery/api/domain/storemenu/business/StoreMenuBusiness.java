package org.delivery.api.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;

import java.util.List;

@Business
@RequiredArgsConstructor
public class StoreMenuBusiness {
    private final StoreMenuConverter storeMenuConverter;
    private final StoreMenuService storeMenuService;

    public List<StoreMenuResponse> search(Long storeId){
        var list = storeMenuService.getStoreMenuByStoreId(storeId);
        return list.stream().map(storeMenuConverter::toResponse).toList();
    }


}
