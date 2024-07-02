package org.delivery.db.review;

import org.delivery.db.review.enums.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Page<ReviewEntity> findAllByStoreIdAndStatusOrderByRegisteredAtDesc(Long storeId, ReviewStatus status, PageRequest pageRequest);
    Page<ReviewEntity> findAllByUserIdAndStatusOrderByRegisteredAtDesc(Long userId, ReviewStatus status, PageRequest pageRequest);
}
