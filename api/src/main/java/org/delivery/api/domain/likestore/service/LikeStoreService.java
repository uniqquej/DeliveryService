package org.delivery.api.domain.likestore.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.likestore.LikeStoreEntity;
import org.delivery.db.likestore.LikeStoreRepository;
import org.delivery.db.likestore.enums.LikeStatus;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<StoreEntity> getLikeStore(Long userId){
        return likeStoreRepository.getLikeStore(userId);
    }

    public List<Long> getLikeStoreId(Long userId){
        return likeStoreRepository.getLikeStoreId(userId);
    }

    @Transactional
    public LikeStoreEntity updateStatus(LikeStoreEntity likeStore, LikeStatus likeStatus) {
        if(likeStatus==LikeStatus.LIKE) likeStore.getStore().likeStore();
        else likeStore.getStore().canceledLikeStore();

        likeStore.setStatus(likeStatus);
        return likeStoreRepository.save(likeStore);

    }

    @Transactional
    public LikeStoreEntity updateStatus(LikeStoreEntity likeStore,Long storeId,
                                        Long userId, LikeStatus likeStatus) {
       var store = storeRepository.findByStoreId(storeId);
       var user = userRepository.getReferenceById(userId);

       user.addLikedStore(likeStore);
       store.likeStore();

        likeStore.setStore(store);
        likeStore.setStatus(likeStatus);

        return likeStoreRepository.save(likeStore);
    }
}
