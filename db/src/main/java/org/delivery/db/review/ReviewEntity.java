package org.delivery.db.review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.review.enums.ReviewStatus;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.user.UserEntity;
import org.delivery.db.userorder.UserOrderEntity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id")
    private StoreEntity store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private UserOrderEntity userOrder;

    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
}
