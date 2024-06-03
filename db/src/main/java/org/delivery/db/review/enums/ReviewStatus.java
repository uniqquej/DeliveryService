package org.delivery.db.review.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReviewStatus {
    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ;

    private String description;
}
