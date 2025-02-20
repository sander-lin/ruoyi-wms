package com.ruoyi.wms.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.wms.domain.entity.Logistics;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;

import java.io.Serializable;
import java.io.Serial;

/**
 * 物流信息视图对象 logistics
 *
 * @author zcc
 * @date 2025-02-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Logistics.class)
public class LogisticsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String id;

    /**
     * 物流信息
     */
    @ExcelProperty(value = "物流信息")
    private String logisticsInfo;

    /**
     * 物流时间
     */
    @ExcelProperty(value = "物流时间")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date logisticsDate;


}
