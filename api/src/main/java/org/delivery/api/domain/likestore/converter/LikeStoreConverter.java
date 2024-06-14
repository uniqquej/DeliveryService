package org.delivery.api.domain.likestore.converter;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.domain.likestore.controller.model.LikeStoreResponse;
import org.delivery.db.likestore.LikeStoreEntity;

@Converter
public class LikeStoreConverter {
    public LikeStoreResponse toResponse(LikeStoreEntity entity){
        return LikeStoreResponse.builder()
                .userId(entity.getUser().getId())
                .storeId(entity.getStore().getId())
                .status(entity.getStatus())
                .build();
    }
}
