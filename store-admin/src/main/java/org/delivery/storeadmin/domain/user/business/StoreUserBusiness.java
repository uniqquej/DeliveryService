package org.delivery.storeadmin.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.delivery.storeadmin.domain.user.service.StoreUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreUserBusiness {
    private final StoreUserConverter storeUserConverter;
    private final StoreUserService storeUserService;
    private final StoreRepository storeRepository;

    public StoreUserResponse register(StoreUserRegisterRequest request){
        var storeEntity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(request.getStoreName(), StoreStatus.REGISTERED);
        var entity = storeUserConverter.toEntity(request,storeEntity.get());
        var savedEntity = storeUserService.register(entity);
        return storeUserConverter.toResponse(savedEntity,storeEntity.get());
    }
}
