package com.ruoyi.wms.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.enums.UserStatus;
import com.ruoyi.common.excel.annotation.CellMerge;
import com.ruoyi.common.excel.annotation.ExcelEnumFormat;
import com.ruoyi.common.excel.convert.ExcelEnumConvert;
import com.ruoyi.wms.enums.OrderStatus;
import com.ruoyi.wms.enums.OrderType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderExcel {
    /**
     *
     */
    @CellMerge
    @ExcelProperty(value = "id")
    private String id;

    /**
     * 所属客户
     */
    @CellMerge
    @ExcelProperty(value = "所属客户")
    private String userId;

    /**
     * 订单类型
     */
    @CellMerge
    @ExcelEnumFormat(enumClass = OrderType.class, textField = "desc")
    @ExcelProperty(value = "订单类型", converter = ExcelEnumConvert.class)
    private String type;

    /**
     * 订单状态
     */
    @CellMerge
    @ExcelProperty(value = "订单状态", converter = ExcelEnumConvert.class)
    @ExcelEnumFormat(enumClass = OrderStatus.class, textField = "desc")
    private String status;

    /**
     * 订单总额
     */
    @CellMerge
    @ExcelProperty(value = "订单总额")
    private String totalAmount;

    /**
     * 备注
     */
    @CellMerge
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     *
     */
    @ExcelProperty(value = { "商品", "id" })
    private String mId;

    /**
     * FNSKU
     */
    @ExcelProperty(value = { "商品", "FNSKU" })
    private String fnsku;

    /**
     * ASIN
     */
    @ExcelProperty(value = { "商品", "ASIN" })
    private String asin;

    /**
     * 商品名称
     */
    @ExcelProperty(value = { "商品", "商品名称" })
    private String name;

    /**
     * 尺寸
     */
    @ExcelProperty(value = { "商品", "尺寸" })
    private String size;

    /**
     * 颜色
     */
    @ExcelProperty(value = { "商品", "颜色" })
    private String color;

    /**
     * 型号
     */
    @ExcelProperty(value = { "商品", "型号" })
    private String mType;

    /**
     * 图片
     */
    @ExcelProperty(value = { "商品", "图片"})
    private String image;

    /**
     * 单价
     */
    @ExcelProperty(value = { "商品", "单价" })
    private BigDecimal price;
}
