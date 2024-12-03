package com.ruoyi.wms.domain.bo;

import com.ruoyi.wms.domain.entity.ShipmentNotice;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;


/**
 * 发货请求通知单业务对象 shipment_notice
 *
 * @author huiwei
 * @date 2024-11-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ShipmentNotice.class, reverseConvertGenerate = false)
public class ShipmentNoticeBo extends BaseEntity {

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { EditGroup.class })
    private String id;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderId;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userId;

    /**
     * 标签种类
     */
    @NotBlank(message = "标签种类不能为空", groups = { AddGroup.class, EditGroup.class })
    private String tag;

    /**
     * 通知单状态
     */
    @NotBlank(message = "通知单状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 配送方式（物流渠道）
     */
    @NotBlank(message = "配送方式（物流渠道）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String deliveryMethod;

    /**
     * 备注
     */
    private String remark;


}
