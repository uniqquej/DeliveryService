package org.delivery.storeadmin.domain.userorder.controller.model;

import lombok.*;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.delivery.storeadmin.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.storeadmin.domain.userordermenu.controller.model.UserOrderMenuResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserOrderDetailResponse {
    private UserOrderResponse userOrderResponse;
    private List<UserOrderMenuResponse> userOrderMenuResponseList;
}

