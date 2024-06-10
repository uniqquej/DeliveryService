package org.delivery.db.userorder;

import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity,Long> {
    List<UserOrderEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId, UserOrderStatus status);
    List<UserOrderEntity> findAllByStoreIdAndStatusOrderByIdDesc(Long storeId, UserOrderStatus status);

    List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> status);
    List<UserOrderEntity> findAllByStoreIdAndStatusInOrderByIdDesc(Long storeId, List<UserOrderStatus> status);
    Optional<UserOrderEntity> findByIdAndStatusAndUserId(Long id, UserOrderStatus status, Long userId);
    Optional<UserOrderEntity> findAllByIdAndUserId(Long id, Long userId);

}
