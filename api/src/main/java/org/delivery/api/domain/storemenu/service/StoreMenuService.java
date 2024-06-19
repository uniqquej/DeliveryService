package org.delivery.api.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.storemenu.StoreMenuRepository;
import org.delivery.db.storemenu.enums.StoreMenuStatusEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreMenuService {
    private final StoreMenuRepository storeMenuRepository;

    public List<StoreMenuEntity> getStoreMenuWithThrow(List<Long> menuIds){
        var storeEntityList = menuIds.stream().map(it->{
          return storeMenuRepository.getReferenceById(it);
        });

        return storeEntityList.toList();
    }

    public List<StoreMenuEntity> getStoreMenuByStoreId(Long storeId){
        return storeMenuRepository.findAllByStoreIdAndStatus(storeId, StoreMenuStatusEnum.REGISTERED);
    }

}
