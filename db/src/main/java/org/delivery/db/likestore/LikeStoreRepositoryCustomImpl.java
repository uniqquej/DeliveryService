package org.delivery.db.likestore;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.delivery.db.likestore.enums.LikeStatus;
import org.delivery.db.likestore.QLikeStoreEntity;
import org.delivery.db.store.StoreEntity;


import java.util.List;
import java.util.Optional;

public class LikeStoreRepositoryCustomImpl implements LikeStoreRepositoryCustom{
    private JPAQueryFactory queryFactory;
    public LikeStoreRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Long> getLikeStoreId(Long userId){
        QLikeStoreEntity likeStore = QLikeStoreEntity.likeStoreEntity;

        return queryFactory
                .select(likeStore.store.id)
                .from(likeStore)
                .where(likeStore.status.eq(LikeStatus.LIKE)
                        .and(likeStore.user.id.eq(userId)))
                .fetch();
    }

    @Override
    public List<StoreEntity> getLikeStore(Long userId){
        QLikeStoreEntity likeStore = QLikeStoreEntity.likeStoreEntity;

        return queryFactory
                .select(likeStore.store)
                .from(likeStore)
                .where(likeStore.status.eq(LikeStatus.LIKE)
                        .and(likeStore.user.id.eq(userId)))
                .fetch();
    }

    @Override
    public Optional<LikeStoreEntity> checkLikeStore(Long storeId, Long userId){
        QLikeStoreEntity likeStore = QLikeStoreEntity.likeStoreEntity;

        return Optional.ofNullable(queryFactory
                .selectFrom(likeStore)
                .where(likeStore.store.id.eq(storeId)
                        .and(likeStore.user.id.eq(userId)))
                .fetchOne());
    }
}
