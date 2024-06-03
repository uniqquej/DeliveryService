package org.delivery.db.review;

import org.delivery.db.review.enums.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByStoreIdAndStatusOrderByIdDesc(Long storeId, ReviewStatus status);
    List<ReviewEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId, ReviewStatus status);
}
