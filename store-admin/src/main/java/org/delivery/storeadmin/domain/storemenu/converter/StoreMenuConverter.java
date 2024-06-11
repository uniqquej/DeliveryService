package org.delivery.storeadmin.domain.storemenu.converter;

import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.storeadmin.domain.storemenu.controller.model.MenuRegisterRequest;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreMenuConverter {
    public StoreMenuResponse toResponse(StoreMenuEntity storeMenuEntity){
        return StoreMenuResponse.builder()
                .id(storeMenuEntity.getId())
                .storeId(storeMenuEntity.getStoreId())
                .name(storeMenuEntity.getName())
                .status(storeMenuEntity.getStatus())
                .amount(storeMenuEntity.getAmount())
                .sequence(storeMenuEntity.getSequence())
                .thumbnailUrl(storeMenuEntity.getThumbnailUrl())
                .likeCount(storeMenuEntity.getLikeCount())
                .build();
    }

    public StoreMenuEntity toEntity(Long storeId, MenuRegisterRequest request, String imgUrl){
        return StoreMenuEntity.builder()
                .storeId(storeId)
                .name(request.getName())
                .amount(request.getAmount())
                .thumbnailUrl(imgUrl)
                .build();
    }
}
