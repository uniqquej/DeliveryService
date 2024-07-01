package org.delivery.api.domain.store.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreEntity getStoreWithMenu(Long id){
        return storeRepository.findStoreWithStoreMenu(id);
    }
    public StoreEntity getStoreEntity(Long id){
        return storeRepository.findById(id).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    @Transactional
    public StoreEntity likeStore(Long id){
        var storeEntity = storeRepository.getReferenceById(id);
        storeEntity.likeStore();
        return storeEntity;
    }

    @Transactional
    public StoreEntity canceledLikeStore(Long id){
        var storeEntity = storeRepository.getReferenceById(id);
        storeEntity.canceledLikeStore();
        return storeEntity;
    }

    public Slice<StoreEntity> search(StoreCategory category, Long lastId, String region, String name,Pageable pageable){
        return storeRepository.findStoreTerminateList(category, lastId,region,name,pageable);
    }


}
