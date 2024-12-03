package com.ruoyi.wms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShipmentNoticeStatus {
    DRAFT("草稿", "1"),
    PENDING("未发货", "2"),
    PART_SHIPPED("部分发货", "3"),
    ALL_SHIPPED("全部发货", "4"),
    COMPLETED("已完成", "5"),
    CLOSED("已关闭", "6");

    private final String desc;
    private final String code;
}
