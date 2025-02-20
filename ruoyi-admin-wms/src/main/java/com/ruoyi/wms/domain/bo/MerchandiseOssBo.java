package com.ruoyi.wms.domain.bo;

import com.ruoyi.wms.domain.entity.MerchandiseOss;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import io.github.linpeilie.annotations.AutoMapper;


/**
 * 相关商品上传文件业务对象 merchandise_oss
 *
 * @author zcc
 * @date 2025-02-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MerchandiseOss.class, reverseConvertGenerate = false)
public class MerchandiseOssBo extends BaseEntity {

    /**
     * 
     */
    @NotBlank(message = "不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 关联商品id
     */
    @NotBlank(message = "关联商品id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String merchandiseId;

    /**
     * 上传文件id
     */
    @NotBlank(message = "上传文件id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String ossId;


}
