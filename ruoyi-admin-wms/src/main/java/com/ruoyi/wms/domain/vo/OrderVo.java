package com.ruoyi.wms.domain.vo;

import com.ruoyi.wms.domain.entity.Merchandise;
import com.ruoyi.wms.domain.entity.Order;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;

import java.io.Serializable;
import java.io.Serial;
import java.util.List;

/**
 * 订单表视图对象 order
 *
 * @author zcc
 * @date 2024-11-11
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Order.class)
public class OrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String id;

    /**
     * 所属客户
     */
    @ExcelProperty(value = "所属客户")
    private String userId;

    /**
     * 订单类型
     */
    @ExcelProperty(value = "订单类型")
    private String type;

    /**
     * 订单状态
     */
    @ExcelProperty(value = "订单状态")
    private String status;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 选项
     */
    @ExcelProperty(value = "选项")
    private String option;

    private List<MerchandiseVo> merchandises;

}
