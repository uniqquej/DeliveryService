package org.delivery.api.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.likestore.business.LikeStoreBusiness;
import org.delivery.api.domain.store.controller.model.StoreDetailResponse;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.store.enums.StoreCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@RequiredArgsConstructor
@Business
public class StoreBusiness {
    private final StoreService storeService;
    private final StoreConverter storeConverter;

    private final StoreMenuConverter storeMenuConverter;
    private final LikeStoreBusiness likeStoreBusiness;

    public StoreDetailResponse storeDetail(Long storeId, User user){
        var likedStoreIds = likeStoreBusiness.checkMyLikeStore(storeId, user);
        var storeEntity = storeService.getStoreWithMenu(storeId);

        var storeResponse = storeConverter.toResponse(storeEntity);

        var menuList = storeEntity.getMenus();
        var menuResponse = storeMenuConverter.toResponse(menuList);

        return StoreDetailResponse.builder()
                .store(storeResponse)
                .menuList(menuResponse)
                .likedStore(likedStoreIds)
                .build();
    }

    public StoreResponse likeStore(Long id){
        var storeEntity = storeService.likeStore(id);
        return storeConverter.toResponse(storeEntity);
    }

    public StoreResponse canceledLikeStore(Long id){
        var storeEntity = storeService.canceledLikeStore(id);
        return storeConverter.toResponse(storeEntity);
    }

    public Slice<StoreResponse> search(StoreCategory category, Pageable pageable, Long lastId, String region,String name){
        var entityList = storeService.search(category, lastId, region,name, pageable);
        var response = entityList.map(storeConverter::toResponse);

        return response;
    }


}
