package org.delivery.api.domain.likestore.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.likestore.controller.model.LikeStoreResponse;
import org.delivery.api.domain.likestore.converter.LikeStoreConverter;
import org.delivery.api.domain.likestore.service.LikeStoreService;
import org.delivery.api.domain.store.business.StoreBusiness;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.likestore.enums.LikeStatus;

@Business
@RequiredArgsConstructor
public class LikeStoreBusiness {
    private final LikeStoreService likeStoreService;
    private final LikeStoreConverter likeStoreConverter;

    private final StoreBusiness storeBusiness;
    private final UserBusiness userBusiness;

    public LikeStoreResponse likeStore(Long storeId, User user){
        var likeStoreEntity = likeStoreService.getLikeStore(storeId,user.getId());

        if(likeStoreEntity.getStatus()== LikeStatus.LIKE) {
            storeBusiness.canceledLikeStore(storeId);
            likeStoreEntity = likeStoreService.updateStatus(likeStoreEntity, LikeStatus.CANCEL);
        }
        else if(likeStoreEntity.getStatus()== LikeStatus.CANCEL){
            storeBusiness.likeStore(storeId);
            likeStoreEntity = likeStoreService.updateStatus(likeStoreEntity, LikeStatus.LIKE);
        }
        else {
            storeBusiness.likeStore(storeId);
            likeStoreEntity = likeStoreService.updateStatus(likeStoreEntity, storeId, user.getId(), LikeStatus.LIKE);
        }

        return likeStoreConverter.toResponse(likeStoreEntity);
    }
}
