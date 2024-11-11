package com.ruoyi.wms.domain.bo;

import com.ruoyi.wms.domain.entity.Financial;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;


/**
 * 资金明细表业务对象 financial
 *
 * @author zcc
 * @date 2024-11-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Financial.class, reverseConvertGenerate = false)
public class FinancialBo extends BaseEntity {

    /**
     * 
     */
    @NotBlank(message = "不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 客户id
     */
    @NotBlank(message = "客户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userId;

    /**
     * 支出，收入状态
     */
    @NotBlank(message = "支出，收入状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String state;

    /**
     * 金额
     */
    @NotBlank(message = "金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private String amount;

    /**
     * 状态变更事件
     */
    @NotBlank(message = "状态变更事件不能为空", groups = { AddGroup.class, EditGroup.class })
    private String event;


}
