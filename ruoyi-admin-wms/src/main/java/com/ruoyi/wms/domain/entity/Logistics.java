package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 物流信息对象 logistics
 *
 * @author zcc
 * @date 2025-02-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("logistics")
public class Logistics extends BaseEntity {

    @Serial
    private static final long serialVersionUID=1L;

    /**
     * 
     */
    @TableId(value = "id")
    private String id;
    /**
     * 关联发货单id
     */
    private String shipmentId;
    /**
     * 物流信息
     */
    private String logisticsInfo;
    /**
     * 物流时间
     */
    private Date logisticsDate;

}
