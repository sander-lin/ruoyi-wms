package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;


import java.io.Serial;

/**
 * 资金明细表对象 financial
 *
 * @author zcc
 * @date 2024-11-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("financial")
public class Financial extends BaseEntity {

    @Serial
    private static final long serialVersionUID=1L;

    /**
     * 
     */
    @TableId(value = "id")
    private String id;
    /**
     * 客户id
     */
    private String userId;
    /**
     * 支出，收入状态
     */
    private String state;
    /**
     * 金额
     */
    private String amount;
    /**
     * 状态变更事件
     */
    private String event;

}
