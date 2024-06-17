package org.delivery.storeadmin.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreEntity getStoreWithThrow(Long storeId){
        return storeRepository.findFirstByIdAndStatusOrderByIdDesc(storeId, StoreStatus.REGISTERED)
                .orElseThrow(()-> new NullPointerException("해당 가게가 없음"));
    }

    public StoreEntity registerStore(StoreEntity store){
        return Optional.ofNullable(store)
                .map(it-> {
                    it.setStatus(StoreStatus.REGISTERED);
                    it.setStar(BigDecimal.ZERO);
                    return storeRepository.save(store);
                })
                .orElseThrow(()->new NullPointerException("가게 등록 오류"));
    }
}
