package org.delivery.storeadmin.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.storeadmin.domain.s3.service.S3UploadService;
import org.delivery.storeadmin.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.storeadmin.domain.store.converter.StoreConverter;
import org.delivery.storeadmin.domain.store.service.StoreService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StoreBusiness {
    private final StoreService storeService;
    private final S3UploadService s3UploadService;

    private final StoreConverter storeConverter;

    public StoreEntity getStoreWithThrow(Long storeId){
        return storeService.getStoreWithThrow(storeId);
    }

    public void registerStore(StoreRegisterRequest storeRegisterRequest, MultipartFile storeImgFile) throws Exception {
        var imgUrl = s3UploadService.upload(storeImgFile, "storeImg/");
        var storeEntity = storeConverter.toEntity(storeRegisterRequest, imgUrl);
        storeService.registerStore(storeEntity);
    }
}
