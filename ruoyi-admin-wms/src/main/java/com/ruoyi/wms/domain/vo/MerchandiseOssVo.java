package com.ruoyi.wms.domain.vo;

import com.ruoyi.wms.domain.entity.MerchandiseOss;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import io.github.linpeilie.annotations.AutoMapper;

import java.io.Serializable;
import java.io.Serial;

/**
 * 相关商品上传文件视图对象 merchandise_oss
 *
 * @author zcc
 * @date 2025-02-17
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MerchandiseOss.class)
public class MerchandiseOssVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private String id;

    /**
     * 关联商品id
     */
    @ExcelProperty(value = "关联商品id")
    private String merchandiseId;

    /**
     * 上传文件id
     */
    @ExcelProperty(value = "上传文件id")
    private String ossId;


}
