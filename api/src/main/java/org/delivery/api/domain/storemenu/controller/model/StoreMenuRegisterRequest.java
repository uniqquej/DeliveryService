package org.delivery.api.domain.storemenu.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storemenu.enums.StoreMenuStatusEnum;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreMenuRegisterRequest {
    private Long storeId;

    private String name;

    private BigDecimal amount;

    private String thumbnailUrl;
}
