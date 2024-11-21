 package com.ruoyi.wms.domain.vo.businessorder;

 import com.ruoyi.common.mybatis.core.domain.BaseVo;
 import com.ruoyi.wms.domain.entity.BusinessOrder;
 import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
 import com.alibaba.excel.annotation.ExcelProperty;
 import com.ruoyi.wms.domain.vo.merchandise.MerchandiseOrderVo;
 import com.ruoyi.wms.domain.vo.merchandise.MerchandiseVo;
 import lombok.Data;
 import io.github.linpeilie.annotations.AutoMapper;
 import lombok.EqualsAndHashCode;

 import java.util.List;

 /**
  * 订单表视图对象 order
  *
  * @author zcc
  * @date 2024-11-11
  */
 @Data
 @ExcelIgnoreUnannotated
 @EqualsAndHashCode(callSuper = true)
 @AutoMapper(target = BusinessOrder.class)
 public class BusinessOrderVo extends BaseVo {
     /**
      *
      */
     @ExcelProperty(value = "")
     private String id;

     /**
      * 所属客户
      */
     @ExcelProperty(value = "所属客户")
     private String userId;

     /**
      * 订单类型
      */
     @ExcelProperty(value = "订单类型")
     private String type;

     /**
      * 订单状态
      */
     @ExcelProperty(value = "订单状态")
     private String status;

     /**
      * 订单总额
      */
     @ExcelProperty(value = "订单总额")
     private String totalAmount;

     /**
      * 备注
      */
     @ExcelProperty(value = "备注")
     private String remark;

     private List<MerchandiseOrderVo> merchandises;
 }
