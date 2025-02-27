package com.ruoyi.wms.domain.vo.merchandise;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.entity.Merchandise;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

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
public class MerchandiseNoticeDetailVo extends MerchandiseVo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 选项
     */
    @ExcelProperty(value = "选项")
    private String labelOption;

    /**
     * 订单需求数量
     */
    @ExcelProperty(value = "订单需求数量")
    private Integer quantityRequired;

    /**
     * 通知发货数量
     */
    @ExcelProperty(value = "通知发货数量")
    private Integer quantityNotice;

    /**
     * 通知发货数量
     */
    @ExcelProperty(value = "总通知发货数量")
    private Integer totalQuantityShipped;
}
