package com.ruoyi.wms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    DRAFT("草稿","1"),
    PAID("买付中","2"),
    DELIVERING("配送中","3"),
    COMPLETED("已完成","4");

    private final String desc;
    private final String code;
}
