package org.delivery.storeadmin.domain.userordermenu.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderMenuResponse {
    private Long count;
    private String menuName;
    private BigDecimal amount;
}
