package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 商品库存表对象 inventory
 *
 * @author zcc
 * @date 2024-11-12
 */
@Data
@EqualsAndHashCode()
@TableName("inventory")
public class Inventories implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 库存ID
     */
    @TableId(value = "id")
    private String id;
    /**
     * 数量
     */
    private Integer number;

    /**
     * 入库时间
     */
    private LocalDateTime entryTime;

    /**
     * 商品id
     */
    private String merchandiseId;
    /**
     * 单位
     */
    private String unit;
    /**
     * 备注
     */
    private String remark;

}
