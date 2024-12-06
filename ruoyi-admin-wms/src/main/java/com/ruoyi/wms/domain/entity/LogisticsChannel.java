package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;


import java.io.Serial;

/**
 * 物流渠道对象 logistics_channel
 *
 * @author zcc
 * @date 2024-11-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("logistics_channel")
public class LogisticsChannel extends BaseEntity {

    @Serial
    private static final long serialVersionUID=1L;

    /**
     * 
     */
    @TableId(value = "id")
    private String id;
    /**
     * 
     */
    private String name;
    /**
     * 
     */
    private String value;

}
