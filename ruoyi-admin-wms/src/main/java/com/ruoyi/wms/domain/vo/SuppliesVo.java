package com.ruoyi.wms.domain.vo;

import com.ruoyi.wms.domain.entity.Supplies;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;

import java.io.Serializable;
import java.io.Serial;

/**
 * 供应商视图对象 supplies
 *
 * @author zcc
 * @date 2025-02-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Supplies.class)
public class SuppliesVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商名称")
    private String name;

    /**
     * 供应商地址
     */
    @ExcelProperty(value = "供应商地址")
    private String address;

    /**
     * 供应商电话
     */
    @ExcelProperty(value = "供应商电话")
    private String phone;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
