package com.ruoyi.wms.service;

import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.wms.domain.bo.businessorder.OrderMerchandiseBo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.domain.entity.OrderMerchandise;
import com.ruoyi.wms.mapper.OrderMerchandiseMapper;

/**
 * 订单商品关系Service业务层处理
 *
 * @author hw
 * @date 2024-11-15
 */
@RequiredArgsConstructor
@Service
public class OrderMerchandiseService {

    private final OrderMerchandiseMapper orderMerchandiseMapper;

    /**
     * 新增订单商品关系
     */
    public void insertByBo(OrderMerchandiseBo bo) {
        OrderMerchandise add = MapstructUtils.convert(bo, OrderMerchandise.class);
        orderMerchandiseMapper.insert(add);
    }

    /**
     * 修改订单商品关系
     */
    public void updateByBo(OrderMerchandiseBo bo) {
        OrderMerchandise update = MapstructUtils.convert(bo, OrderMerchandise.class);
        orderMerchandiseMapper.updateById(update);
    }

}
