package com.ruoyi.wms.domain.vo;

import com.ruoyi.wms.domain.entity.Finances;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;

import java.io.Serializable;
import java.io.Serial;

/**
 * 资金明细视图对象 finances
 *
 * @author zcc
 * @date 2024-10-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Finances.class)
public class FinancesVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 充值金额
     */
    @ExcelProperty(value = "充值金额")
    private Long rechargeMoney;

    /**
     * 余额
     */
    @ExcelProperty(value = "余额")
    private Long balance;

    /**
     * 账单类型
     */
    @ExcelProperty(value = "账单类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "wms_finances_type")
    private String type;

    /**
     * 账单子类型
     */
    @ExcelProperty(value = "账单子类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "wms_finances_sub_type")
    private String subType;

    /**
     * 关联用户id
     */
    @ExcelProperty(value = "关联用户id")
    private Long userId;

}
