package com.ruoyi.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;


import java.io.Serial;

/**
 * 相关商品上传文件对象 merchandise_oss
 *
 * @author zcc
 * @date 2025-02-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("merchandise_oss")
public class MerchandiseOss extends BaseEntity {

    @Serial
    private static final long serialVersionUID=1L;

    /**
     * 
     */
    @TableId(value = "id")
    private String id;
    /**
     * 关联商品id
     */
    private String merchandiseId;
    /**
     * 上传文件id
     */
    private String ossId;

}
