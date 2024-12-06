package com.ruoyi.wms.domain.vo.financial;

import com.ruoyi.wms.domain.entity.Financial;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.mybatis.core.domain.BaseVo;

import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;

import java.io.Serial;

/**
 * 资金明细表视图对象 financial
 *
 * @author zcc
 * @date 2024-11-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Financial.class)
public class FinancialVo extends BaseVo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String id;

    /**
     * 客户id
     */
    @ExcelProperty(value = "客户id")
    private String userId;

    /**
     * 支出，收入状态
     */
    @ExcelProperty(value = "支出，收入状态")
    private String state;

    /**
     * 金额
     */
    @ExcelProperty(value = "金额")
    private String amount;

    /**
     * 状态变更事件
     */
    @ExcelProperty(value = "状态变更事件")
    private String event;

}
