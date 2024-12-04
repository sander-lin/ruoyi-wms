package com.ruoyi.wms.domain.bo.financial;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import com.ruoyi.wms.enums.FinancialState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 用户余额业务对象 user_balance
 *
 * @author Huiwei
 * @date 2024-11-29
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class NewFinanceBo extends BaseEntity {

    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userId;

    /**
     * 金额
     */
    @NotBlank(message = "金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private String amount;

    /**
     * 支出 1 收入 0
     */
    @Null
    private String state;

    /**
     * 事件
     */
    @Null
    private String event;


}
