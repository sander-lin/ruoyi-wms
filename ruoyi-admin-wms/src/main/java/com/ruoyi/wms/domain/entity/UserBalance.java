package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import java.io.Serial;

/**
 * 用户余额对象 user_balance
 *
 * @author Huiwei
 * @date 2024-11-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_balance")
public class UserBalance extends BaseEntity {

    @Serial
    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 余额
     */
    private BigDecimal balance;

}
