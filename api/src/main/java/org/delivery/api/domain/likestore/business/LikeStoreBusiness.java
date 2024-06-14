package org.delivery.api.domain.likestore.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.likestore.controller.model.LikeStoreResponse;
import org.delivery.api.domain.likestore.converter.LikeStoreConverter;
import org.delivery.api.domain.likestore.service.LikeStoreService;
import org.delivery.api.domain.store.business.StoreBusiness;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.likestore.enums.LikeStatus;

@Business
@RequiredArgsConstructor
public class LikeStoreBusiness {
    private final LikeStoreService likeStoreService;
    private final LikeStoreConverter likeStoreConverter;

    private final StoreBusiness storeBusiness;
    private final StoreConverter storeConverter;

    private final UserConverter userConverter;

    public LikeStoreResponse likeStore(Long storeId, User user){
        var likeStoreEntity = likeStoreService.getLikeStore(storeId,user.getId());

        System.out.println(likeStoreEntity.toString());

        if(likeStoreEntity.getStatus()== LikeStatus.LIKE) {
            storeBusiness.canceledLikeStore(storeId);
            likeStoreEntity = likeStoreService.updateStatus(likeStoreEntity, LikeStatus.CANCEL);
        }
        else if(likeStoreEntity.getStatus()== LikeStatus.CANCEL){
            storeBusiness.likeStore(storeId);
            likeStoreEntity = likeStoreService.updateStatus(likeStoreEntity, LikeStatus.LIKE);
        }
        else {
            var storeResponse = storeBusiness.getStoreById(storeId);
            var storeEntity = storeConverter.toEntity(storeResponse);
            var userEntity = userConverter.toEntity(user);
            likeStoreEntity = likeStoreService.updateStatus(likeStoreEntity, storeEntity, userEntity, LikeStatus.LIKE);
        }

        return likeStoreConverter.toResponse(likeStoreEntity);
    }
}
