package com.ruoyi.wms.mapper;

import com.ruoyi.wms.domain.bo.businessorder.OrderMerchandiseBo;
import com.ruoyi.wms.domain.bo.shipmentnotice.ShipmentNoticeMerchandiseBo;
import com.ruoyi.wms.domain.entity.OrderMerchandise;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

/**
 * 订单商品关系Mapper接口
 *
 * @author hw
 * @date 2024-11-15
 */
public interface OrderMerchandiseMapper extends BaseMapperPlus<OrderMerchandise, OrderMerchandiseBo> {
}
