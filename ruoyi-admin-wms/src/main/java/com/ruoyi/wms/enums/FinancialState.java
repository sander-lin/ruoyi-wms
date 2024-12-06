package com.ruoyi.wms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinancialState {
    INCOME("收入", "0"),
    EXPENDITURE("支出","1");

    private final String desc;
    private final String code;
}
