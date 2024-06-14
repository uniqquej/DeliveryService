package org.delivery.db.store;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.review.ReviewEntity;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.hibernate.annotations.Fetch;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Entity
@Table(name="store")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class StoreEntity extends BaseEntity {
    @Column(length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<StoreMenuEntity> menus;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews;

    @Column(length = 150, nullable = false)
    private String address;

    @Column(length = 50, nullable = false, columnDefinition = "varchar(50)")
    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @Column(length = 50, nullable = false, columnDefinition = "varchar(50)")
    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    private BigDecimal star;

    @Column(length = 200, nullable = false)
    private String thumbnailUrl;

    @Column(precision=11, scale = 4, nullable = false)
    private BigDecimal minimumDeliveryPrice;

    @Column(length = 20)
    private String phoneNumber;

    private int likes;

    public void addMenu(StoreMenuEntity menuEntity){
        menus.add(menuEntity);
        menuEntity.setStore(this);
    }

    public void addReview(ReviewEntity reviewEntity){
        reviews.add(reviewEntity);
        reviewEntity.setStore(this);
    }

    public void likeStore(){
        this.likes += 1;
    }

    public void canceledLikeStore(){
        this.likes -= 1;
    }

    public BigDecimal calculateStar(){
        BigDecimal sumStar = BigDecimal.ZERO;

        if(reviews.isEmpty()) return sumStar;

        for(ReviewEntity review: reviews){
            sumStar = sumStar.add(BigDecimal.valueOf(review.getStar()));
        }

        BigDecimal avgStar = sumStar.divide(BigDecimal.valueOf(reviews.size()));

        return avgStar.setScale(2, RoundingMode.HALF_EVEN);
    }

}
