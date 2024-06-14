package org.delivery.db.likestore;

import org.delivery.db.likestore.enums.LikeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeStoreRepository extends JpaRepository<LikeStoreEntity, Long> {

    @Query("SELECT ls FROM LikeStoreEntity ls WHERE ls.store.id = :storeId and ls.user.id = :userId")
    Optional<LikeStoreEntity> checkLikeStore(@Param(value = "storeId") Long storeId,
                                             @Param(value = "userId")Long userId);

    @Query("SELECT ls FROM LikeStoreEntity ls JOIN FETCH ls.store WHERE ls.user.id = :userId and ls.status = :status")
    List<LikeStoreEntity> checkLikeStore(@Param(value = "userId")Long userId, @Param(value = "status") LikeStatus status);

}
