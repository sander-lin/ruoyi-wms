package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;


import java.io.Serial;
import java.io.Serializable;

/**
 * 请求发货通知商品关系对象 shipment_notice_merchandise
 *
 * @author Huiwei
 * @date 2024-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("shipment_notice_merchandise")
public class ShipmentNoticeMerchandise implements Serializable {

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
     * 发货通知单id
     */
    private String shipmentNoticeId;
    /**
     * 通知数量
     */
    private String quantityNotice;

}
