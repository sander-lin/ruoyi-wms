package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;


import java.io.Serial;

/**
 * 资金明细对象 finances
 *
 * @author zcc
 * @date 2024-10-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("finances")
public class Finances extends BaseEntity {

    @Serial
    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 充值金额
     */
    private Long rechargeMoney;
    /**
     * 余额
     */
    private Long balance;
    /**
     * 账单类型
     */
    private String type;
    /**
     * 账单子类型
     */
    private String subType;

}
