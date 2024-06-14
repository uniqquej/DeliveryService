package org.delivery.api.domain.likestore.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.delivery.db.likestore.LikeStoreEntity;
import org.delivery.db.likestore.LikeStoreRepository;
import org.delivery.db.likestore.enums.LikeStatus;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.user.UserEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeStoreService {
    private final LikeStoreRepository likeStoreRepository;

    public LikeStoreEntity getLikeStore(Long storeId, Long userId){
        return likeStoreRepository.checkLikeStore(storeId,userId)
                .orElseGet(LikeStoreEntity::new);
    }

    public LikeStoreEntity updateStatus(LikeStoreEntity likeStore, LikeStatus likeStatus) {
        System.out.println("update status");
        likeStore.setStatus(likeStatus);
        return likeStoreRepository.save(likeStore);

    }

    public LikeStoreEntity updateStatus(LikeStoreEntity likeStore,StoreEntity store,
                                        UserEntity user, LikeStatus likeStatus) {
        System.out.printf("new entity update status : ");
        System.out.println(likeStore.toString());
        likeStore.setStore(store);
        likeStore.setUser(user);
        likeStore.setStatus(likeStatus);

        System.out.println(likeStore.toString());

        return likeStoreRepository.save(likeStore);
    }
}
