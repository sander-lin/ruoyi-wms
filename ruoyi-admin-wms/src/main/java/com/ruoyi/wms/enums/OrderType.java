package com.ruoyi.wms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {
    SAMPLE("样本订单", "1"),
    PRODUCTION("量产订单","2");

    private final String desc;
    private final String code;
}
