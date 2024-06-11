package org.delivery.api.domain.store.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.db.store.StoreEntity;

import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class StoreConverter {
    public StoreEntity toEntity(StoreRegisterRequest request){
        return Optional.ofNullable(request)
                .map(it->{
                    return StoreEntity.builder()
                            .name(request.getName())
                            .address(request.getAddress())
                            .category(request.getCategory())
                            .thumbnailUrl(request.getThumbnailUrl())
                            .minimumDeliveryPrice(request.getMinimumDeliveryPrice())
                            .phoneNumber(request.getPhoneNumber())
                            .build();

                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreResponse toResponse(StoreEntity entity){
        return Optional.ofNullable(entity)
                .map(it->{
                    return StoreResponse.builder()
                            .id(entity.getId())
                            .name(entity.getName())
                            .address(entity.getAddress())
                            .category(entity.getCategory())
                            .status(entity.getStatus())
                            .star(entity.getStar())
                            .minimumDeliveryPrice(entity.getMinimumDeliveryPrice())
                            .thumbnailUrl(entity.getThumbnailUrl())
                            .phoneNumber(entity.getPhoneNumber())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));

    }
}
