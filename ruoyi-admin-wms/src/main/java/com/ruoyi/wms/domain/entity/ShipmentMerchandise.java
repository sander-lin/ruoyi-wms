package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;


import java.io.Serial;
import java.io.Serializable;

/**
 * 发货单商品关系对象 shipment_merchandise
 *
 * @author Huiwei
 * @date 2024-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("shipment_merchandise")
public class ShipmentMerchandise implements Serializable {

    @Serial
    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private String id;
    /**
     * 商品id
     */
    private String merchandiseId;
    /**
     * 发货单id
     */
    private String shipmentId;
    /**
     * 已发数量
     */
    private String quantityShipped;

}
