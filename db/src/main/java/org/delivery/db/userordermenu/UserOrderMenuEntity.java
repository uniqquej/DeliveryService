package org.delivery.db.userordermenu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;

@Entity
@Table(name = "user_order_menu")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderMenuEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private UserOrderEntity userOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private StoreMenuEntity menu;

    @Column(nullable = false)
    private Long count;

    @Column(nullable = false, length = 50, columnDefinition = "varchar(50)")
    @Enumerated(EnumType.STRING)
    private UserOrderMenuStatus status;

    public static UserOrderMenuEntity createUserOrderMenu(StoreMenuEntity storeMenuEntity, Long count){
        UserOrderMenuEntity orderMenuEntity = new UserOrderMenuEntity();
        orderMenuEntity.setMenu(storeMenuEntity);
        orderMenuEntity.setCount(count);
        orderMenuEntity.setStatus(UserOrderMenuStatus.REGISTERED);
        return orderMenuEntity;
    }

}
