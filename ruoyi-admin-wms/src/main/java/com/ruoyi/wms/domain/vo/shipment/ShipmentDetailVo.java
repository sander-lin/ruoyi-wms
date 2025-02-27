package com.ruoyi.wms.domain.vo.shipment;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.mybatis.core.domain.BaseVo;
import com.ruoyi.wms.domain.entity.Shipment;
import com.ruoyi.wms.domain.vo.LogisticsVo;
import com.ruoyi.wms.domain.vo.merchandise.MerchandiseShipmentDetailVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 发货管理视图对象 shipment
 *
 * @author zcc
 * @date 2024-11-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Shipment.class)
@EqualsAndHashCode(callSuper = true)
public class ShipmentDetailVo extends BaseVo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String id;

    /**
     * 发货通知单编号
     */
    @ExcelProperty(value = "发货通知单编号")
    private String shipmentNoticeId;

    /**
     * 发货状态
     */
    @ExcelProperty(value = "发货状态")
    private String status;

    /**
     * 物流渠道
     */
    @ExcelProperty(value = "物流渠道")
    private String deliveryMethod;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 标签种类
     */
    @ExcelProperty(value = "标签种类")
    private String tag;

    /**
     * 物流单号
     */
    @ExcelProperty(value = "物流单号")
    private String logisticsNumber;

    /**
     * 发货时间
     */
    @ExcelProperty(value = "发货时间")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime deliveryTime;

    /**
     * 商品信息
     */
    private List<MerchandiseShipmentDetailVo> merchandises;


    /**
     * 物流信息
     */
    private List<LogisticsVo> logisticsList;
}
