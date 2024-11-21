package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;


import java.io.Serial;

/**
 * 发货请求通知单对象 shipment_notice
 *
 * @author huiwei
 * @date 2024-11-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("shipment_notice")
public class ShipmentNotice extends BaseEntity {

    @Serial
    private static final long serialVersionUID=1L;

    /**
     * 
     */
    @TableId(value = "id")
    private String id;
    /**
     * 
     */
    private String orderId;
    /**
     * 
     */
    private String userId;
    /**
     * 标签种类
     */
    private String tag;
    /**
     * 通知单状态
     */
    private String status;
    /**
     * 配送方式（物流渠道）
     */
    private String deliveryMethod;
    /**
     * 备注
     */
    private String remark;

}
