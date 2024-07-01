package org.delivery.db.store;

import org.delivery.db.store.enums.StoreCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface StoreRepositoryCustom {
    Slice<StoreEntity> findStoreTerminateList(StoreCategory category, Long lastId, String region,
                                              String name,Pageable pageable);
}
