package com.ruoyi.wms.domain.bo;

import com.ruoyi.wms.domain.entity.LogisticsChannel;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;


/**
 * 物流渠道业务对象 logistics_channel
 *
 * @author zcc
 * @date 2024-11-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = LogisticsChannel.class, reverseConvertGenerate = false)
public class LogisticsChannelBo extends BaseEntity {

    /**
     * 
     */
    @NotBlank(message = "不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String value;


}
