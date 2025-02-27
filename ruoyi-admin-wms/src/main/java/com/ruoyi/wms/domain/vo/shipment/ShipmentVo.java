package com.ruoyi.wms.domain.vo.shipment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.mybatis.core.domain.BaseVo;
import com.ruoyi.wms.domain.entity.Shipment;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 发货管理视图对象 shipment
 *
 * @author zcc
 * @date 2024-11-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Shipment.class)
@EqualsAndHashCode(callSuper = true)
public class ShipmentVo extends BaseVo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String id;

    /**
     * 发货通知单编号
     */
    @ExcelProperty(value = "发货通知单编号")
    private String shipmentNoticeId;

    /**
     * 发货状态
     */
    @ExcelProperty(value = "发货状态")
    private String status;

    /**
     * 物流渠道
     */
    @ExcelProperty(value = "物流渠道")
    private String deliveryMethod;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 物流单号
     */
    @ExcelProperty(value = "物流单号")
    private String logisticsNumber;

    /**
     * 发货时间
     */
    @ExcelProperty(value = "发货时间")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime deliveryTime;
}
