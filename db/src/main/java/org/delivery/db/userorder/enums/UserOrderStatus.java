package org.delivery.db.userorder.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserOrderStatus {
    REGISTERED("등록"),
    UNREGISTERED("해지"),
    CANCELED("취소"),
    ORDER("주문"),
    ACCEPT("확인"),
    COOKING("조리시작"),
    DELIVERY("배달중"),
    RECEIVE("완료"),
    ;

    private final String description;
}
