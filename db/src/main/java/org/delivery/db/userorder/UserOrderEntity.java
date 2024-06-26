package org.delivery.db.userorder;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.review.ReviewEntity;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.user.UserEntity;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.delivery.db.userordermenu.UserOrderMenuEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_order")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderEntity extends BaseEntity {
    @Column(nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @OneToMany(mappedBy = "userOrder",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> review = new ArrayList<>();

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserOrderMenuEntity> orderMenus = new ArrayList<>();

    @Column(nullable = false, length = 50, columnDefinition = "varchar(50)")
    @Enumerated(EnumType.STRING)
    private UserOrderStatus status;

    @Column(precision=11, scale = 4, nullable = false)
    private BigDecimal totalPrice;

    private boolean hasReview = false;

    private LocalDateTime orderedAt;

    private LocalDateTime acceptedAt;

    private LocalDateTime cookingStartedAt;

    private LocalDateTime deliveryStartedAt;

    private LocalDateTime receivedAt;

    private LocalDateTime cancelledAt;

    public static UserOrderEntity createUserOrder(UserEntity user, List<UserOrderMenuEntity> userOrderMenuEntityList){
        UserOrderEntity orderEntity = new UserOrderEntity();
        orderEntity.setUser(user);
        for(UserOrderMenuEntity menuEntity : userOrderMenuEntityList){
            orderEntity.addMenu(menuEntity);
        }
        return orderEntity;
    }
    public void addMenu(UserOrderMenuEntity userOrderMenu){
        orderMenus.add(userOrderMenu);
        userOrderMenu.setUserOrder(this);
    }

    public void addReview(ReviewEntity reviewEntity){
        review.add(reviewEntity);
        hasReview = true;
        reviewEntity.setUserOrder(this);
    }

    public BigDecimal getTotalPrice(){
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(UserOrderMenuEntity menuEntity:orderMenus){
            totalPrice = totalPrice.add(
                    menuEntity.getMenu().getPrice()
                            .multiply(BigDecimal.valueOf(menuEntity.getCount()))
            );
        }
        return totalPrice;
    }



}
