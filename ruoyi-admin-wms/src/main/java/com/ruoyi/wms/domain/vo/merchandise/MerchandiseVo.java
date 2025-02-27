package com.ruoyi.wms.domain.vo.merchandise;

import java.math.BigDecimal;
import com.ruoyi.wms.domain.entity.Merchandise;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;

import java.io.Serializable;
import java.io.Serial;
import java.util.List;

/**
 * 商品管理视图对象 merchandise
 *
 * @author zcc
 * @date 2024-11-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Merchandise.class)
public class MerchandiseVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "id")
    private String id;

    /**
     * FNSKU
     */
    @ExcelProperty(value = "FNSKU")
    private String fnsku;

    /**
     * ASIN
     */
    @ExcelProperty(value = "ASIN")
    private String asin;

    /**
     * 商品名称
     */
    @ExcelProperty(value = "商品名称")
    private String name;

    /**
     * 尺寸
     */
    @ExcelProperty(value = "尺寸")
    private String size;

    /**
     * 颜色
     */
    @ExcelProperty(value = "颜色")
    private String color;

    /**
     * 型号
     */
    @ExcelProperty(value = "型号")
    private String type;

    /**
     * 图片
     */
    @ExcelProperty(value = "图片")
    private String image;

    /**
     * 上传文件地址
     */
    @ExcelProperty(value = "文件列表")
    private List<File> files;

    /**
     * 所属用户
     */
    @ExcelProperty(value = "所属用户")
    private String userId;

    /**
     * 商品库存数量
     */
    @ExcelProperty(value = "商品库存数量")
    private Integer inventoryQuantity;

    /**
     * 单价
     */
    @ExcelProperty(value = "单价")
    private BigDecimal price;
}
