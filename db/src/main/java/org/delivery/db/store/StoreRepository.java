package org.delivery.db.store;

import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    Optional<StoreEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreStatus status);
    Optional<StoreEntity> findFirstByNameAndStatusOrderByIdDesc(String name, StoreStatus status);

    List<StoreEntity> findAllByStatusAndCategoryOrderByStarDesc(StoreStatus status, StoreCategory category);

    @Query("SELECT s FROM StoreEntity s JOIN FETCH s.menus WHERE s.id = :storeId")
    StoreEntity findStoreWithStoreMenu(@Param("storeId") Long id);

    List<StoreEntity> findByNameContaining(String name);

    @Query("SELECT s FROM StoreEntity s WHERE s.status = :storeStatus AND s.category = :category " +
            "AND s.address LIKE %:region%")
    List<StoreEntity> findAllByCategoryAndRegion(StoreStatus storeStatus, StoreCategory category, String region);
}
