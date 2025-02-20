 package com.ruoyi.wms.domain.bo.businessorder;

 import com.ruoyi.wms.domain.entity.BusinessOrder;
 import com.ruoyi.common.core.validate.AddGroup;
 import com.ruoyi.common.core.validate.EditGroup;
 import com.ruoyi.common.mybatis.core.domain.BaseEntity;
 import com.ruoyi.wms.enums.OrderStatus;
 import lombok.Data;
 import lombok.EqualsAndHashCode;
 import jakarta.validation.constraints.*;
 import io.github.linpeilie.annotations.AutoMapper;

 import java.time.LocalDateTime;
 import java.util.Date;


 /**
  * 订单表业务对象 order
  * 获取订单列表
  */

 @Data
 @EqualsAndHashCode(callSuper = true)
 @AutoMapper(target = BusinessOrder.class, reverseConvertGenerate = false)
 public class BusinessOrderBo extends BaseEntity {

     /**
      *
      */
     @NotBlank(message = "不能为空", groups = { EditGroup.class })
     private String id;

     /**
      * 所属客户
      */
     @NotBlank(message = "所属客户不能为空", groups = { AddGroup.class, EditGroup.class })
     private String userId;

     /**
      * 商品名称
      */
     private String name;

     /**
      * 起始日期
      */
     private LocalDateTime startTime;

     /**
      * 终止日期
      */
     private LocalDateTime endTime;

     /**
      * 订单类型
      */
     @NotBlank(message = "订单类型不能为空", groups = { AddGroup.class, EditGroup.class })
     private String type;

     /**
      * 订单状态
      */
     @NotBlank(message = "订单状态不能为空", groups = { AddGroup.class, EditGroup.class })
     private String status;

     /**
      * 备注
      */
     private String remark;
 }
