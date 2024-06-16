package org.delivery.storeadmin.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.store.business.StoreBusiness;
import org.delivery.storeadmin.domain.storemenu.controller.model.MenuRegisterRequest;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.S3UploadService;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreMenuBusiness {

    @Value("${menuImgLocation}")
    private String menuImgLocation;

    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    private final StoreBusiness storeBusiness;

    private final S3UploadService s3UploadService;

    public List<StoreMenuResponse> getStoreMenuList(Long storeId){
        var storeEntity = storeBusiness.getStoreWithThrow(storeId);
        var storeMenuEntityList = storeMenuService.getStoreMenuByStoreId(storeId);
        return storeMenuEntityList.stream().map(it->{
            return storeMenuConverter.toResponse(it, storeEntity);
        }).toList();
    }

    public void registerMenu(Long storeId, MenuRegisterRequest menuRegisterRequest, MultipartFile menuImgFile) throws Exception {
        var imgUrl = s3UploadService.upload(menuImgFile);
        var storeMenuEntity = storeMenuConverter.toEntity(menuRegisterRequest, imgUrl);
        storeMenuService.registerMenu(storeId, storeMenuEntity);
    }

    private String uploadFile(String uploadPath, String originalFileName, byte[] fileData)throws Exception{
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }
}
