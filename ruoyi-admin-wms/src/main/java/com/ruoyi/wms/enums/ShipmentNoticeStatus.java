package com.ruoyi.wms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShipmentNoticeStatus {
    PENDING("未发货", "1"),
    PART_SHIPPED("部分发货", "2"),
    ALL_SHIPPED("全部发货", "3"),
    COMPLETED("已完成", "4"),
    CLOSED("已关闭", "5");

    private final String description;
    private final String code;
}
