package org.delivery.storeadmin.domain.store.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.storeadmin.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.storeadmin.domain.store.controller.model.StoreResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreConverter {
    public StoreEntity toEntity(StoreRegisterRequest request, String imgUrl){
        return Optional.ofNullable(request)
                .map(it->{
                    return StoreEntity.builder()
                            .name(request.getName())
                            .address(request.getAddress())
                            .category(request.getCategory())
                            .thumbnailUrl(imgUrl)
                            .minimumDeliveryPrice(request.getMinimumDeliveryPrice())
                            .phoneNumber(request.getPhoneNumber())
                            .build();

                })
                .orElseThrow(()-> new NullPointerException("상품 등록 오류"));
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
                            .likes(entity.getLikes())
                            .minimumDeliveryPrice(entity.getMinimumDeliveryPrice())
                            .thumbnailUrl(entity.getThumbnailUrl())
                            .phoneNumber(entity.getPhoneNumber())
                            .build();
                })
                .orElseThrow(()-> new NullPointerException("상품 오류"));

    }
}
