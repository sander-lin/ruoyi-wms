package com.ruoyi.wms.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;
import java.util.Date;

/**
 * 发货管理对象 shipment
 *
 * @author zcc
 * @date 2024-11-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("shipment")
public class Shipment extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
    private String id;
    /**
     * 发货通知单编号
     */
    private String shipmentNoticeId;
    /**
     * 发货状态
     */
    private String status;
    /**
     * 物流渠道
     */
    private String deliveryMethod;
    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 物流单号
     */
    private String logisticsNumber;

    /**
     * 发货时间
     */
    private Date deliveryTime;
}
