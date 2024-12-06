package com.ruoyi.wms.domain.vo.financial;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.mybatis.core.domain.BaseVo;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.wms.domain.entity.Financial;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.util.List;

/**
 * 资金明细表视图对象 financial
 *
 * @author zcc
 * @date 2024-11-08
 */
@Data
@ExcelIgnoreUnannotated
public class FinancialTableInfoVo extends TableDataInfo<FinancialVo> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 总支出
     */
    @ExcelProperty(value = "总支出")
    private String totalExpenditure = "0";

    /**
     * 总收入
     */
    @ExcelProperty(value = "总收入")
    private String totalIncome ="0";

}
