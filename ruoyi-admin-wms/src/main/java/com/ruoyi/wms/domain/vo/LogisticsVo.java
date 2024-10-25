package com.ruoyi.wms.domain.vo;

import com.ruoyi.wms.domain.entity.Logistics;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.mybatis.core.domain.BaseVo;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;

import java.io.Serial;

/**
 * 物流渠道视图对象 wms_logistics
 *
 * @author zcc
 * @date 2024-10-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Logistics.class)
public class LogisticsVo extends BaseVo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 物流渠道名称
     */
    @ExcelProperty(value = "物流渠道名称")
    private String logisticsName;

}
