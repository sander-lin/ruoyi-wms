package com.ruoyi.wms.domain.bo.shipment;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.wms.domain.entity.Shipment;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class NewShipmentBo extends BaseEntity {

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 发货通知单编号
     */
    @NotBlank(message = "发货通知单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String shipmentNoticeId;

    /**
     * 发货状态
     */
    @NotBlank(message = "发货状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 物流渠道
     */
    @NotBlank(message = "物流渠道不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deliveryMethod;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

    /**
     * 物流单号
     */
    private String logisticsNumber;

    private List<ShipmentMerchandiseBo> merchandises;
}
