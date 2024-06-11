package org.delivery.api.domain.storemenu.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.storemenu.StoreMenuEntity;

import java.util.List;
import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class StoreMenuConverter {

    public StoreMenuEntity toEntity(StoreMenuRegisterRequest request){
        return Optional.ofNullable(request)
                .map(it->{
                    return StoreMenuEntity.builder()
                            .name(request.getName())
                            .price(request.getPrice())
                            .thumbnailUrl(request.getThumbnailUrl())
                            .build();
                }).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreMenuResponse toResponse(StoreMenuEntity entity){
        return Optional.ofNullable(entity)
                .map(it->{
                    return StoreMenuResponse.builder()
                            .id(entity.getId())
                            .storeId(entity.getStore().getId())
                            .status(entity.getStatus())
                            .name(entity.getName())
                            .amount(entity.getPrice())
                            .thumbnailUrl(entity.getThumbnailUrl())
                            .likeCount(entity.getLikeCount())
                            .sequence(entity.getSequence())
                            .build();
                }).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT));
    }

    public List<StoreMenuResponse> toResponse(List<StoreMenuEntity> entityList){
        return entityList.stream()
                .map(it->toResponse(it))
                .toList();
    }
}
