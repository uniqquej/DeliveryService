package org.delivery.api.domain.likestore.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.likestore.LikeStoreEntity;
import org.delivery.db.likestore.LikeStoreRepository;
import org.delivery.db.likestore.enums.LikeStatus;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeStoreService {
    private final LikeStoreRepository likeStoreRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public LikeStoreEntity getLikeStore(Long storeId, Long userId){
        return likeStoreRepository.checkLikeStore(storeId,userId)
                .orElseGet(LikeStoreEntity::new);
    }

    public List<LikeStoreEntity> getLikeStore(Long userId){
        return likeStoreRepository.checkLikeStore(userId, LikeStatus.LIKE);
    }

    public LikeStoreEntity updateStatus(LikeStoreEntity likeStore, LikeStatus likeStatus) {
        System.out.println("update status");
        likeStore.setStatus(likeStatus);
        return likeStoreRepository.save(likeStore);

    }

    public LikeStoreEntity updateStatus(LikeStoreEntity likeStore,Long storeId,
                                        Long userId, LikeStatus likeStatus) {
       var store = storeRepository.getReferenceById(storeId);
       var user = userRepository.getReferenceById(userId);

        likeStore.setStore(store);
        likeStore.setUser(user);
        likeStore.setStatus(likeStatus);

        return likeStoreRepository.save(likeStore);
    }
}
