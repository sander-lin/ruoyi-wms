package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;


import java.io.Serial;
import java.math.BigDecimal;

/**
 * 订单表对象 order
 *
 * @author zcc
 * @date 2024-11-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("business_order")
public class BusinessOrder extends BaseEntity {

    @Serial
    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private String id;
    /**
     * 所属客户
     */
    private String userId;
    /**
     * 订单类型
     */
    private String type;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 订单总额
     */
    private BigDecimal totalAmount;
}
