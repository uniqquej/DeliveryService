package org.delivery.storeadmin.domain.storemenu.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuRegisterRequest {
    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;
}
