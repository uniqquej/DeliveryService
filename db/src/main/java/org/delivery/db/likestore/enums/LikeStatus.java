package org.delivery.db.likestore.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LikeStatus {
    LIKE("좋아요"),
    CANCEL("좋아요 취소");

    private String description;
}
