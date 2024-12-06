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
public class MerchandiseShipmentCreatingVo extends MerchandiseVo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 选项
     */
    @ExcelProperty(value = "选项")
    private String labelOption;

    /**
     * 通知发货数量
     */
    @ExcelProperty(value = "通知发货数量")
    private String quantityNotice;

    /**
     * 发货数量
     */
    @ExcelProperty(value = "库存剩余")
    private String stock;

    /**
     * 通知发货数量
     */
    @ExcelProperty(value = "总已发货数量(包含本次)")
    private String totalQuantityShipped;
}
