package com.ruoyi.wms.domain.bo;

import com.ruoyi.wms.domain.entity.ShipmentMerchandise;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;


/**
 * 发货单商品关系业务对象 shipment_merchandise
 *
 * @author Huiwei
 * @date 2024-11-19
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ShipmentMerchandise.class, reverseConvertGenerate = false)
public class ShipmentMerchandiseBo extends BaseEntity {

    /**
     * 
     */
    @NotBlank(message = "不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 商品id
     */
    @NotBlank(message = "商品id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String merchandiseId;

    /**
     * 发货单id
     */
    @NotBlank(message = "发货单id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String shipmentId;

    /**
     * 已发数量
     */
    @NotBlank(message = "已发数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private String quantityShipped;


}
