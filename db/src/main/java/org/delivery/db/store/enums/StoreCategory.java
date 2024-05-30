package org.delivery.db.store.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreCategory {
    CHINESE_FOOD("중식","중식"),
    KOREAN_FOOD("양식","양식"),
    JAPANESE_FOOD("한식","한식"),
    CHICKEN("치킨","치킨"),
    PIZZA("피자","피자"),
    HAMBURGER("햄버거","햄버거"),
    DESSERT("디저트","디저트"),
    ;

    private String display;
    private String description;
}
