package org.delivery.db.user.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {
    ADMIN("관리자"),
    USER("일반 유저"),
    ;

    private final String description;
}
