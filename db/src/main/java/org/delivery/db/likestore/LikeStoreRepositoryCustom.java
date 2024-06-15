package org.delivery.db.likestore;

import org.delivery.db.likestore.enums.LikeStatus;
import org.delivery.db.store.StoreEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeStoreRepositoryCustom {
    Optional<LikeStoreEntity> checkLikeStore(Long storeId, Long userId);

    List<StoreEntity> getLikeStore(Long userId);

    List<Long> getLikeStoreId(Long userId);
}
