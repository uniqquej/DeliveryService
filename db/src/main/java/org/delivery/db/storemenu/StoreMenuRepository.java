package org.delivery.db.storemenu;

import org.delivery.db.storemenu.enums.StoreMenuStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreMenuRepository extends JpaRepository<StoreMenuEntity,Long> {

    List<StoreMenuEntity> findAllByStoreIdAndStatus(Long storeId, StoreMenuStatusEnum status);
}
