package com.ruoyi.wms.service;

import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.domain.bo.OrderBo;
import com.ruoyi.wms.domain.vo.OrderVo;
import com.ruoyi.wms.domain.entity.Order;
import com.ruoyi.wms.mapper.OrderMapper;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单表Service业务层处理
 *
 * @author zcc
 * @date 2024-11-11
 */
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderMapper orderMapper;

    /**
     * 查询订单表
     */
    public OrderVo queryById(String id){
        return orderMapper.selectVoById(id);
    }

    /**
     * 查询订单表列表
     */
    public TableDataInfo<OrderVo> queryPageList(OrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Order> lqw = buildQueryWrapper(bo);
        Page<OrderVo> result = orderMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询订单表列表
     */
    public List<OrderVo> queryList(OrderBo bo) {
        LambdaQueryWrapper<Order> lqw = buildQueryWrapper(bo);
        return orderMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Order> buildQueryWrapper(OrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Order> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getUserId()), Order::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), Order::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), Order::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getOption()), Order::getOption, bo.getOption());
        return lqw;
    }

    /**
     * 新增订单表
     */
    public void insertByBo(OrderBo bo) {
        Order add = MapstructUtils.convert(bo, Order.class);
        orderMapper.insert(add);
    }

    /**
     * 修改订单表
     */
    public void updateByBo(OrderBo bo) {
        Order update = MapstructUtils.convert(bo, Order.class);
        orderMapper.updateById(update);
    }

    /**
     * 批量删除订单表
     */
    public void deleteByIds(Collection<String> ids) {
        orderMapper.deleteBatchIds(ids);
    }
}
