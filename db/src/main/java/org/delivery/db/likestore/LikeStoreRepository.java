package org.delivery.db.likestore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface LikeStoreRepository extends JpaRepository<LikeStoreEntity, Long>,
        QuerydslPredicateExecutor<LikeStoreEntity>, LikeStoreRepositoryCustom {

}
