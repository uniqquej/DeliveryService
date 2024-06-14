package org.delivery.db.likestore;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.likestore.enums.LikeStatus;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.user.UserEntity;

@Entity
@Table(name = "like_store")
@NoArgsConstructor
@AllArgsConstructor
@Data @SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class LikeStoreEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

}
