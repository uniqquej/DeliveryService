package org.delivery.db.userorder;

import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity,Long> {
    List<UserOrderEntity> findAllByStoreIdAndStatusOrderByIdDesc(Long storeId, UserOrderStatus status);
    List<UserOrderEntity> findAllByStoreIdAndStatusInOrderByIdDesc(Long storeId, List<UserOrderStatus> status);

    @Query("SELECT o FROM UserOrderEntity o JOIN FETCH o.orderMenus WHERE o.user.id = :userId" +
            " AND o.status in :statusList")
    List<UserOrderEntity> findOrderWithOrderMenu(@Param("userId") Long userId, @Param("statusList") List<UserOrderStatus> statusList);

    @Query("SELECT o FROM UserOrderEntity o JOIN FETCH o.orderMenus WHERE o.user.id = :userId AND o.id = :orderId")
    Optional<UserOrderEntity> findOrderByIdAndUser(
            @Param("userId") Long userId, @Param("orderId") Long id);

}
