package com.ruoyi.wms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.wms.domain.bo.OrderBo;
import com.ruoyi.wms.domain.entity.Order;
import com.ruoyi.wms.domain.vo.OrderVo;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表Mapper接口
 *
 * @author zcc
 * @date 2024-11-11
 */
public interface OrderMapper extends BaseMapperPlus<Order, OrderVo> {
    Page<OrderVo> selectOrderList(Page<OrderVo> page, @Param("bo") OrderBo bo);
    int SelectOrderCount(@Param("bo") OrderBo bo);
}
