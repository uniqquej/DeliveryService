package org.delivery.storeadmin.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreEntity getStoreWithThrow(Long storeId){
        return storeRepository.findFirstByIdAndStatusOrderByIdDesc(storeId, StoreStatus.REGISTERED)
                .orElseThrow(()-> new NullPointerException("해당 가게가 없음"));
    }
}
