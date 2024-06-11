package org.delivery.storeadmin.domain.storemenu.business;

import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.store.business.StoreBusiness;
import org.delivery.storeadmin.domain.storemenu.controller.model.MenuRegisterRequest;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

    public List<StoreMenuResponse> getStoreMenuList(Long storeId){
        var storeEntity = storeBusiness.getStoreWithThrow(storeId);
        var storeMenuEntityList = storeMenuService.getStoreMenuByStoreId(storeId);
        return storeMenuEntityList.stream().map(it->{
            return storeMenuConverter.toResponse(it, storeEntity);
        }).toList();
    }

    public void registerMenu(Long storeId, MenuRegisterRequest menuRegisterRequest, MultipartFile menuImgFile) throws Exception {
        var storeEntity = storeBusiness.getStoreWithThrow(storeId);
        String originImgName = menuImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(originImgName)){
            imgName = uploadFile(menuImgLocation, originImgName,menuImgFile.getBytes());
            imgUrl = "/images/menu/"+imgName;
        }

        var storeMenuEntity = storeMenuConverter.toEntity(storeEntity, menuRegisterRequest,imgUrl);
        storeMenuService.registerMenu(storeMenuEntity);
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
