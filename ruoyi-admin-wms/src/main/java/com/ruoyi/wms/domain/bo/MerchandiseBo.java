package com.ruoyi.wms.domain.bo;

import com.ruoyi.wms.domain.entity.Merchandise;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;

import java.math.BigDecimal;

/**
 * 商品管理业务对象 merchandise
 *
 * @author zcc
 * @date 2024-11-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Merchandise.class, reverseConvertGenerate = false)
public class MerchandiseBo extends BaseEntity {

    /**
     * 
     */
    @NotBlank(message = "id不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * FNSKU
     */
    @NotBlank(message = "FNSKU不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fnsku;

    /**
     * ASIN
     */
    @NotBlank(message = "ASIN不能为空", groups = { AddGroup.class, EditGroup.class })
    private String asin;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 尺寸
     */
    @NotBlank(message = "尺寸不能为空", groups = { AddGroup.class, EditGroup.class })
    private String size;

    /**
     * 颜色
     */
    @NotBlank(message = "颜色不能为空", groups = { AddGroup.class, EditGroup.class })
    private String color;

    /**
     * 型号
     */
    @NotBlank(message = "型号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 图片
     */
    @NotBlank(message = "图片不能为空", groups = { AddGroup.class, EditGroup.class })
    private String image;

    /**
     * 所属用户
     */
    @NotBlank(message = "所属用户不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userId;

    /**
     * 单价
     */
    @NotNull(message = "单价不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal price;

}
