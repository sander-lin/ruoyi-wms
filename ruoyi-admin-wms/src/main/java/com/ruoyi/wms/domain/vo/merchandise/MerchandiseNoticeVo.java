package com.ruoyi.wms.domain.vo.merchandise;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.Merchandise;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品管理视图对象 merchandise
 *
 * @author zcc
 * @date 2024-11-08
 */
@Data
@ExcelIgnoreUnannotated
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Merchandise.class)
public class MerchandiseNoticeVo extends MerchandiseVo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 通知发货数量
     */
    @ExcelProperty(value = "通知发货数量")
    private Integer quantityNotice;
}
