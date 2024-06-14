package org.delivery.api.domain.likestore.controller.model;

import lombok.Builder;
import lombok.Data;
import org.delivery.db.likestore.enums.LikeStatus;

@Builder
@Data
public class LikeStoreResponse {
    private Long storeId;
    private Long userId;
    private LikeStatus status;

}
