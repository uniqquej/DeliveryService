package org.delivery.api.domain.storemenu.controller.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storemenu.enums.StoreMenuStatusEnum;

import java.math.BigDecimal;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMenuResponse {
    private Long id;

    private Long storeId;

    private String name;

    private BigDecimal amount;

    private StoreMenuStatusEnum status;

    private String thumbnailUrl;

    private int likeCount;

    private int sequence;
}
