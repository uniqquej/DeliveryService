package org.delivery.storeadmin.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreMenuBusiness {
    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    public List<StoreMenuResponse> getStoreMenuList(Long storeId){
        var storeMenuEntityList = storeMenuService.getStoreMenuByStoreId(storeId);
        return storeMenuEntityList.stream().map(storeMenuConverter::toResponse).toList();
    }
}
