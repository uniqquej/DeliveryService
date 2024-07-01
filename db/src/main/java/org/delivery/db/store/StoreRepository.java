package org.delivery.db.store;

import org.delivery.db.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> ,
        QuerydslPredicateExecutor<StoreEntity>, StoreRepositoryCustom {
    Optional<StoreEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreStatus status);
    Optional<StoreEntity> findFirstByNameAndStatusOrderByIdDesc(String name, StoreStatus status);

    @Query("SELECT s FROM StoreEntity s WHERE s.id = :storeId")
    StoreEntity findByStoreId(@Param("storeId") Long id);

    @Query("SELECT s FROM StoreEntity s JOIN FETCH s.menus WHERE s.id = :storeId")
    StoreEntity findStoreWithStoreMenu(@Param("storeId") Long id);
}
