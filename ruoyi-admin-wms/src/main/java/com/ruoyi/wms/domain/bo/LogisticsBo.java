package com.ruoyi.wms.domain.bo;

import com.ruoyi.wms.domain.entity.Logistics;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;

/**
 * 物流渠道业务对象 wms_logistics
 *
 * @author zcc
 * @date 2024-10-22
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Logistics.class, reverseConvertGenerate = false)
public class LogisticsBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 物流渠道名称
     */
    @NotBlank(message = "物流渠道名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String logisticsName;

}
