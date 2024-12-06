package com.ruoyi.wms.domain.vo;

import com.ruoyi.wms.domain.entity.LogisticsChannel;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;

import java.io.Serializable;
import java.io.Serial;

/**
 * 物流渠道视图对象 logistics_channel
 *
 * @author zcc
 * @date 2024-11-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LogisticsChannel.class)
public class LogisticsChannelVo implements Serializable {

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
    private String name;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private String value;


}
