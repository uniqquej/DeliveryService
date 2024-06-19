package org.delivery.db.review;

import org.delivery.db.review.enums.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByStoreIdAndStatusOrderByRegisteredAtDesc(Long storeId, ReviewStatus status);
    List<ReviewEntity> findAllByUserIdAndStatusOrderByRegisteredAtDesc(Long userId, ReviewStatus status);
}
