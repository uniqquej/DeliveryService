package org.delivery.api.domain.review.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRegisterRequest {
    @NotBlank
    private String content;

    @NotNull
    private int star;

    @NotNull
    private Long userOrderId;

    @NotNull
    private Long storerId;
}
