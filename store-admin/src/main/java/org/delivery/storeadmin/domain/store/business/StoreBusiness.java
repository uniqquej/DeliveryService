package org.delivery.storeadmin.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.storeadmin.domain.store.service.StoreService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreBusiness {
    private final StoreService storeService;

    public StoreEntity getStoreWithThrow(Long storeId){
        return storeService.getStoreWithThrow(storeId);
    }
}
