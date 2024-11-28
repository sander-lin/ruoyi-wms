package com.ruoyi.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.wms.domain.bo.shipment.ShipmentBo;
import com.ruoyi.wms.domain.entity.Shipment;
import com.ruoyi.wms.domain.vo.shipment.ShipmentDetailVo;
import com.ruoyi.wms.domain.vo.shipment.ShipmentVo;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

/**
 * 发货管理Mapper接口
 *
 * @author zcc
 * @date 2024-11-08
 */
public interface ShipmentMapper extends BaseMapperPlus<Shipment, ShipmentVo> {
    ShipmentDetailVo selectShipmentById(@Param("id") long id);
}
