package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;

/**
 * 物流渠道对象 wms_logistics
 *
 * @author zcc
 * @date 2024-10-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wms_logistics")
public class Logistics extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 物流渠道名称
     */
    private String logisticsName;

}
