package com.ruoyi.wms.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.mybatis.core.domain.BaseVo;
import com.ruoyi.wms.domain.entity.Inventories;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.vo.merchandise.MerchandiseVo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 商品库存表视图对象 inventory
 *
 * @author zcc
 * @date 2024-11-12
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Inventories.class)
public class InventoriesVo extends BaseVo {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 库存ID
     */
    @ExcelProperty(value = "库存ID")
    private String id;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    private String number;

    /**
     * 商品id
     */
    @ExcelProperty(value = "商品id")
    private String merchandiseId;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位")
    private String unit;

    /**
     * 入库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime entryTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 商品详情
     */
    private MerchandiseVo merchandise;

}
