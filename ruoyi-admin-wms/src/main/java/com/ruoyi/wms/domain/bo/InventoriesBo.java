package com.ruoyi.wms.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.wms.domain.entity.Inventories;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;

/**
 * 商品库存表业务对象 inventory
 *
 * @author zcc
 * @date 2024-11-12
 */

@Data
@EqualsAndHashCode()
@AutoMapper(target = Inventories.class, reverseConvertGenerate = false)
public class InventoriesBo implements Serializable {

    /**
     * 库存ID
     */
    @NotBlank(message = "库存ID不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 数量
     */
    @NotBlank(message = "数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private String number;

    /**
     * 商品id
     */
    @NotBlank(message = "商品id不能为空", groups = { AddGroup.class })
    private String merchandiseId;

    /**
     * 单位
     */
    @NotBlank(message = "单位不能为空", groups = { AddGroup.class })
    private String unit;

    /**
     * 入库时间
     */
    @NotBlank(message = "入库时间不能为空", groups = { AddGroup.class })
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime entryTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 商品名称
     */
    private String merchandiseName;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
