package org.delivery.db.store;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class StoreRepositoryCustomImpl implements StoreRepositoryCustom{
    private JPAQueryFactory queryFactory;
    public StoreRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Slice<StoreEntity> findStoreTerminateList(StoreCategory category, Long lastId,
                                                     String region, String name, Pageable pageable) {
        QStoreEntity storeEntity = QStoreEntity.storeEntity;
        List<StoreEntity> results = queryFactory
                .select(storeEntity)
                .from(storeEntity)
                .where(
                        ltStoreId(lastId),
                        storeEntity.status.eq(StoreStatus.REGISTERED),
                        eqCategory(category),
                        likeRegion(region),
                        likeName(name)
                )
                .orderBy(storeEntity.id.desc())
                .limit(pageable.getPageSize()+1)
                .fetch();

        return new SliceImpl<>(results, pageable, hasNextPage(results, pageable.getPageSize()));
    }

    @Override
    public List<StoreEntity> findStores() {
        QStoreEntity storeEntity = QStoreEntity.storeEntity;
        List<StoreEntity> results = queryFactory
                .select(storeEntity)
                .from(storeEntity)
                .fetch();

        return results;
    }

    private boolean hasNextPage(List<StoreEntity> contents, int pageSize){
        if(contents.size() > pageSize){
            contents.remove(pageSize);
            return true;
        }
        return false;
    }

    private BooleanExpression ltStoreId(Long lastId){
       return lastId == null? null
               : QStoreEntity.storeEntity.id.lt(lastId);
    }

    private BooleanExpression eqCategory(StoreCategory category){
        return (category ==null) ? null
                    : QStoreEntity.storeEntity.category.eq(category);
    }

    private BooleanExpression likeRegion(String region){
        return region ==null? null
                : QStoreEntity.storeEntity.address.like("%"+region+"%");
    }

    private BooleanExpression likeName(String name){
        return name ==null? null
                : QStoreEntity.storeEntity.name.like("%"+name+"%");
    }


}
