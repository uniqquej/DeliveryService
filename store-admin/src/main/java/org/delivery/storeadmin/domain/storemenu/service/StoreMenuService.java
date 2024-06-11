package org.delivery.storeadmin.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.storemenu.StoreMenuRepository;
import org.delivery.db.storemenu.enums.StoreMenuStatusEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreMenuService {
    private final StoreMenuRepository storeMenuRepository;

    public StoreMenuEntity getStoreMenuWithThrow(Long id){
        return storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreMenuStatusEnum.REGISTERED)
                .orElseThrow(()->new RuntimeException(("메뉴를 찾지 못했습니다.")));
    }

    public List<StoreMenuEntity> getStoreMenuByStoreId(Long storeId){
        return storeMenuRepository.findAllByStoreIdAndStatusOrderBySequenceDesc(storeId,StoreMenuStatusEnum.REGISTERED);
    }

    public StoreMenuEntity registerMenu(StoreMenuEntity storeMenuEntity) {
        return Optional.ofNullable(storeMenuEntity)
                .map(it-> {
                            storeMenuEntity.setStatus(StoreMenuStatusEnum.REGISTERED);
                            return storeMenuRepository.save(storeMenuEntity);
                        })
                .orElseThrow(()->new NullPointerException("storeMenuEntity 등록 실패"));
    }
}
