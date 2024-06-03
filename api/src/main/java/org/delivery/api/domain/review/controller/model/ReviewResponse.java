package org.delivery.api.domain.review.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.review.enums.ReviewStatus;

import java.time.LocalDateTime;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private String content;
    private int star;
    private ReviewStatus status;
    private Long userId;
    private Long storeId;
    private Long userOrderId;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
}
