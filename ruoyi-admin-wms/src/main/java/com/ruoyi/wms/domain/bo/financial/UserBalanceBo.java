package com.ruoyi.wms.domain.bo.financial;

import com.ruoyi.wms.domain.entity.UserBalance;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;

import java.math.BigDecimal;

/**
 * 用户余额业务对象 user_balance
 *
 * @author Huiwei
 * @date 2024-11-29
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = UserBalance.class, reverseConvertGenerate = false)
public class UserBalanceBo extends BaseEntity {

    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userId;

    /**
     * 余额
     */
    @NotNull(message = "余额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal balance;


}
