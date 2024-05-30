package org.delivery.db.storemenu;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.storemenu.enums.StoreMenuStatusEnum;

import java.math.BigDecimal;

@Entity
@Table(name = "store_menu")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMenuEntity extends BaseEntity {
    @Column(nullable = false)
    private Long storeId;

    @Column(length = 100,nullable = false)
    private String name;

    @Column(precision = 11, scale = 4,nullable = false)
    private BigDecimal amount;

    @Column(length = 50,nullable = false, columnDefinition = "varchar(50)")
    @Enumerated(EnumType.STRING)
    private StoreMenuStatusEnum status;

    @Column(length = 200,nullable = false)
    private String thumbnailUrl;

    private int likeCount;
    private int sequence;
}
