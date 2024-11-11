package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;

import java.math.BigDecimal;

import java.io.Serial;

/**
 * 商品管理对象 merchandise
 *
 * @author zcc
 * @date 2024-11-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("merchandise")
public class Merchandise extends BaseEntity {

    @Serial
    private static final long serialVersionUID=1L;

    /**
     * 
     */
    @TableId(value = "id")
    private String id;
    /**
     * FNSKU
     */
    private String fnsku;
    /**
     * ASIN
     */
    private String asin;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 尺寸
     */
    private String size;
    /**
     * 颜色
     */
    private String color;
    /**
     * 型号
     */
    private String type;
    /**
     * 图片
     */
    private String image;
    /**
     * 所属用户
     */
    private String userId;
    /**
     * 单价
     */
    private BigDecimal price;

}
