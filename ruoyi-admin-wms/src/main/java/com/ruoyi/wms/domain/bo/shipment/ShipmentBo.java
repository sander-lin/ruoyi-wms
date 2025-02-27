package com.ruoyi.wms.domain.bo.shipment;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.wms.domain.entity.Logistics;
import com.ruoyi.wms.domain.entity.Shipment;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.wms.domain.vo.LogisticsVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


/**
 * 发货管理业务对象 shipment
 *
 * @author zcc
 * @date 2024-11-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Shipment.class, reverseConvertGenerate = false)
public class ShipmentBo extends BaseEntity {

    /**
     *
     */
    @NotBlank(message = "发货单id不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 发货通知单编号
     */
    @NotBlank(message = "发货通知单编号不能为空", groups = { AddGroup.class })
    private String shipmentNoticeId;

    /**
     * 发货状态
     */
    @NotBlank(message = "发货状态不能为空", groups = { AddGroup.class })
    private String status;

    /**
     * 物流渠道
     */
    @NotBlank(message = "物流渠道不能为空", groups = { AddGroup.class })
    private String deliveryMethod;

    /**
     * 发货时间
     */
    @NotBlank(message = "物流渠道不能为空", groups = { AddGroup.class })
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime deliveryTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 物流单号
     */
    private String logisticsNumber;


}
