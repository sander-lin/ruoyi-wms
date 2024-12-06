package com.ruoyi.wms.domain.vo;

import com.ruoyi.wms.domain.entity.ShipmentNoticeMerchandise;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;

import java.io.Serializable;
import java.io.Serial;

/**
 * 请求发货通知商品关系视图对象 shipment_notice_merchandise
 *
 * @author Huiwei
 * @date 2024-11-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ShipmentNoticeMerchandise.class)
public class ShipmentNoticeMerchandiseVo implements Serializable {

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
     * 发货通知单id
     */
    @ExcelProperty(value = "发货通知单id")
    private String shipmentNoticeId;

    /**
     * 通知数量
     */
    @ExcelProperty(value = "通知数量")
    private String quantityNotice;


}
