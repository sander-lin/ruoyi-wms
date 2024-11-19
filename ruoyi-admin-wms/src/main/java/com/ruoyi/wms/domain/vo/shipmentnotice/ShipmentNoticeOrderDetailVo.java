package com.ruoyi.wms.domain.vo.shipmentnotice;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.wms.domain.vo.MerchandiseVo;
import com.ruoyi.wms.domain.vo.OptionItemVo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@ExcelIgnoreUnannotated
public class ShipmentNoticeOrderDetailVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String id;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String orderId;

    /**
     *
     */
    private OptionItemVo user;

    /**
     * 标签种类
     */
    @ExcelProperty(value = "标签种类")
    private String tag;

    /**
     * 通知单状态
     */
    @ExcelProperty(value = "通知单状态")
    private String status;

    /**
     * 配送方式（物流渠道）
     */
    private OptionItemVo deliveryMethodItem;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    private List<MerchandiseVo> merchandises;
}
