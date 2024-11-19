package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;


import java.io.Serial;
import java.io.Serializable;

/**
 * 订单商品关系对象 order_merchandise
 *
 * @author hw
 * @date 2024-11-15
 */
@Data
@EqualsAndHashCode()
@TableName("order_merchandise")
public class OrderMerchandise implements Serializable {

    @Serial
    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private String id;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 商品id
     */
    private String merchandiseId;
    /**
     * 需求数量
     */
    private String quantity_required;
    /**
     * 选项
     */
    private String labelOption;

}
