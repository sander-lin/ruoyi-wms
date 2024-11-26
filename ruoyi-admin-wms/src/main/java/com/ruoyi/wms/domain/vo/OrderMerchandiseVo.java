package com.ruoyi.wms.domain.bo.businessorder;

import com.ruoyi.wms.domain.entity.OrderMerchandise;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;

import java.io.Serial;
import java.io.Serializable;


/**
 * 订单商品关系业务对象 order_merchandise
 *
 * @author hw
 * @date 2024-11-15
 */

@Data
@EqualsAndHashCode(callSuper=false)
@AutoMapper(target = OrderMerchandise.class, reverseConvertGenerate = false)
public class OrderMerchandiseVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 商品id
     */
    @NotBlank(message = "商品id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String merchandiseId;

    /**
     * 需求数量
     */
    @NotBlank(message = "需求数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private String quantityRequired;

    /**
     * 选项
     */
    @NotBlank(message = "选项不能为空", groups = { AddGroup.class, EditGroup.class })
    private String labelOption;
}
