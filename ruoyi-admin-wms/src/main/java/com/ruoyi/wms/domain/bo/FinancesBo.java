package com.ruoyi.wms.domain.bo;

import com.ruoyi.wms.domain.entity.Finances;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;

/**
 * 资金明细业务对象 finances
 *
 * @author zcc
 * @date 2024-10-24
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Finances.class, reverseConvertGenerate = false)
public class FinancesBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 充值金额
     */
    @NotNull(message = "充值金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long rechargeMoney;

    /**
     * 余额
     */
    @NotNull(message = "余额不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long balance;

    /**
     * 账单类型
     */
    @NotBlank(message = "账单类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 账单子类型
     */
    @NotBlank(message = "账单子类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String subType;

    /**
     * 关联用户id
     */
    @NotNull(message = "关联用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;
}
