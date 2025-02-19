package com.ruoyi.wms.domain.bo;

import com.ruoyi.wms.domain.entity.Supplies;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;


/**
 * 供应商业务对象 supplies
 *
 * @author zcc
 * @date 2025-02-19
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Supplies.class, reverseConvertGenerate = false)
public class SuppliesBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 供应商名称
     */
    @NotBlank(message = "供应商名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 供应商地址
     */
    @NotBlank(message = "供应商地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String address;

    /**
     * 供应商电话
     */
    @NotBlank(message = "供应商电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phone;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
