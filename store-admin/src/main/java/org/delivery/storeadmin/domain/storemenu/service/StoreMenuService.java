package org.delivery.storeadmin.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.storemenu.StoreMenuRepository;
import org.delivery.db.storemenu.enums.StoreMenuStatusEnum;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreMenuService {
    private final StoreMenuRepository storeMenuRepository;

    public StoreMenuEntity getStoreMenuWithThrow(Long id){
        return storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreMenuStatusEnum.REGISTERED)
                .orElseThrow(()->new RuntimeException(("메뉴를 찾지 못했습니다.")));
    }
}
