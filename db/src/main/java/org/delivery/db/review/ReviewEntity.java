package org.delivery.db.review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.review.enums.ReviewStatus;

import java.time.LocalDateTime;

@Entity
@Table(name="review")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity extends BaseEntity {
    @Column(length = 200, nullable = false)
    private String content;

    @Column(nullable = false)
    private int star;

    @Column(length = 50, nullable = false, columnDefinition = "varchar(50)")
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private Long userOrderId;

    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
}
