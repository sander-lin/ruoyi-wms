package com.ruoyi.wms.domain.vo;

import com.ruoyi.wms.domain.entity.ShipmentMerchandise;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;

import java.io.Serializable;
import java.io.Serial;

/**
 * 发货单商品关系视图对象 shipment_merchandise
 *
 * @author Huiwei
 * @date 2024-11-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ShipmentMerchandise.class)
public class ShipmentMerchandiseVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private String id;

    /**
     * 商品id
     */
    @ExcelProperty(value = "商品id")
    private String merchandiseId;

    /**
     * 发货单id
     */
    @ExcelProperty(value = "发货单id")
    private String shipmentId;

    /**
     * 已发数量
     */
    @ExcelProperty(value = "已发数量")
    private String quantityShipped;


}
