package com.ruoyi.wms.domain.bo;

import com.ruoyi.wms.domain.entity.Logistics;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 物流信息业务对象 logistics
 *
 * @author zcc
 * @date 2025-02-20
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Logistics.class, reverseConvertGenerate = false)
public class LogisticsBo extends BaseEntity {

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 关联发货单id
     */
    @NotBlank(message = "关联发货单id不能为空", groups = { AddGroup.class })
    private String shipmentId;

    /**
     * 物流信息
     */
    @NotBlank(message = "物流信息不能为空", groups = { AddGroup.class, EditGroup.class })
    private String logisticsInfo;

    /**
     * 物流时间
     */
    @NotNull(message = "物流时间不能为空", groups = { AddGroup.class, EditGroup.class })
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date logisticsDate;


}
