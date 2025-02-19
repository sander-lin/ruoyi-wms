package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;


import java.io.Serial;

/**
 * 供应商对象 supplies
 *
 * @author zcc
 * @date 2025-02-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("supplies")
public class Supplies extends BaseEntity {

    @Serial
    private static final long serialVersionUID=1L;

    /**
     * 
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 供应商名称
     */
    private String name;
    /**
     * 供应商地址
     */
    private String address;
    /**
     * 供应商电话
     */
    private String phone;
    /**
     * 备注
     */
    private String remark;

}
