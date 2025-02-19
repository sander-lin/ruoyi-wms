package com.ruoyi.wms.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.satoken.utils.LoginHelper;
import com.ruoyi.wms.domain.bo.businessorder.BusinessOrderBo;
import com.ruoyi.wms.domain.bo.businessorder.NewOrderBo;
import com.ruoyi.wms.domain.bo.businessorder.UpdateOrderStatusBo;
import com.ruoyi.wms.domain.bo.financial.NewFinanceBo;
import com.ruoyi.wms.domain.entity.BusinessOrder;
import com.ruoyi.wms.domain.entity.Merchandise;
import com.ruoyi.wms.domain.entity.OrderMerchandise;
import com.ruoyi.wms.domain.entity.ShipmentNotice;
import com.ruoyi.wms.domain.vo.OrderMerchandiseVo;
import com.ruoyi.wms.domain.vo.businessorder.BusinessOrderDetailVo;
import com.ruoyi.wms.domain.vo.businessorder.BusinessOrderVo;
import com.ruoyi.wms.domain.vo.shipmentnotice.ShipmentNoticeVo;
import com.ruoyi.wms.enums.FinancialState;
import com.ruoyi.wms.enums.OrderStatus;
import com.ruoyi.wms.mapper.OrderMerchandiseMapper;
import com.ruoyi.wms.mapper.ShipmentNoticeMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.mapper.BusinessOrderMapper;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单表Service业务层处理
 *
 * @author zcc
 * @date 2024-11-11
 */
@RequiredArgsConstructor
@Service
public class BusinessOrderService {

    private final BusinessOrderMapper businessOrderMapper;
    private final OrderMerchandiseService orderMerchandiseService;
    private final OrderMerchandiseMapper orderMerchandiseMapper;
    private final ShipmentNoticeMapper shipmentNoticeMapper;
    private final ShipmentNoticeService shipmentNoticeService;
    private final UserBalanceService userBalanceService;

    /**
     * 查询订单表
     */
    public BusinessOrderVo queryById(String id) {
        return businessOrderMapper.selectVoById(id);
    }

    public List<BusinessOrderVo> queryByIds(List<String> ids) {
        return businessOrderMapper.selectVoBatchIds(ids);
    }

    public BusinessOrderDetailVo queryOrderDetailById(String id) {
        return businessOrderMapper.selectOrderDetailById(Long.valueOf(id));
    }

    /**
     * 查询订单表列表
     */
    public TableDataInfo<BusinessOrderVo> queryPageList(BusinessOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BusinessOrder> lqw = buildLambdaQueryWrapper(bo);
        Page<BusinessOrderVo> result = businessOrderMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public TableDataInfo<BusinessOrderVo> queryOrderList(BusinessOrderBo bo, PageQuery pageQuery) {
        QueryWrapper<BusinessOrder> qw = buildQueryWrapper(bo);
        qw.ne("o.status", OrderStatus.DRAFT.getCode());

        Page<BusinessOrderVo> result = businessOrderMapper.queryOrderList(pageQuery.build(), qw);
        return TableDataInfo.build(result);
    }

    public TableDataInfo<BusinessOrderVo> queryDraftOrderList(BusinessOrderBo bo, PageQuery pageQuery) {
        String userId = Objects.requireNonNull(LoginHelper.getUserId()).toString();
        QueryWrapper<BusinessOrder> qw = buildQueryWrapper(bo);
        qw.eq("o.status", OrderStatus.DRAFT.getCode());
        qw.eq("o.user_id", userId);
        Page<BusinessOrderVo> result = businessOrderMapper.queryOrderList(pageQuery.build(), qw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询订单表列表
     */
    public List<BusinessOrderVo> queryList(BusinessOrderBo bo) {
        LambdaQueryWrapper<BusinessOrder> lqw = buildLambdaQueryWrapper(bo);
        return businessOrderMapper.selectVoList(lqw);
    }

    private QueryWrapper<BusinessOrder> buildQueryWrapper(BusinessOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<BusinessOrder> qw = Wrappers.query();
        qw.eq(StringUtils.isNotBlank(bo.getUserId()), "o.user_id", bo.getUserId());
        qw.eq(StringUtils.isNotBlank(bo.getType()), "o.type", bo.getType());
        qw.eq("o.is_delete", false);
        qw.eq(StringUtils.isNotBlank(bo.getStatus()), "o.status", bo.getStatus());
        qw.ge(!Objects.isNull(bo.getStartTime()), "o.create_time", bo.getStartTime());
        qw.le(!Objects.isNull(bo.getEndTime()),"o.create_time", bo.getEndTime());
        qw.like(StringUtils.isNotBlank(bo.getName()),"m.name", bo.getName());

        qw.orderByDesc("o.update_time");

        return qw;
    }

    private LambdaQueryWrapper<BusinessOrder> buildLambdaQueryWrapper(BusinessOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BusinessOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getUserId()), BusinessOrder::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), BusinessOrder::getType, bo.getType());
        lqw.eq(BusinessOrder::getIsDelete, false);
        lqw.orderByDesc(BusinessOrder::getUpdateTime);
        return lqw;
    }

    /**
     * 新增订单表
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertByBo(NewOrderBo bo) {
        String userId = Objects.requireNonNull(LoginHelper.getUserId()).toString();
        bo.setUserId(userId);
        BusinessOrder businessOrder = MapstructUtils.convert(bo, BusinessOrder.class);
        businessOrderMapper.insert(businessOrder);
        bo.getMerchandises().forEach(merchandise -> {
            merchandise.setOrderId(businessOrder.getId());
            orderMerchandiseService.insertByBo(merchandise);
        });
    }

    /**
     * 付款
     */
    private void checkAndUpdateUserBalance(@NotNull BusinessOrder bo) {
        NewFinanceBo newFinanceBo = new NewFinanceBo();
        newFinanceBo.setUserId(bo.getUserId());
        newFinanceBo.setState(FinancialState.EXPENDITURE.getCode());
        newFinanceBo.setEvent("订单支出： " + bo.getId());
        newFinanceBo.setAmount(bo.getTotalAmount().toString());
        userBalanceService.updateByBo(newFinanceBo);
    }

    /**
     * 退款
     */
    private void refundForOrder(@NotNull BusinessOrder bo) {
        String userId = Objects.requireNonNull(LoginHelper.getUserId()).toString();
        String totalAmount = businessOrderMapper.selectVoById(bo.getId()).getTotalAmount().toString();
        NewFinanceBo newFinanceBo = new NewFinanceBo();
        newFinanceBo.setUserId(userId);
        newFinanceBo.setState(FinancialState.INCOME.getCode());
        newFinanceBo.setEvent("订单退款： " + bo.getId());
        newFinanceBo.setAmount(totalAmount);
        userBalanceService.updateByBo(newFinanceBo);
    }

    /**
     * 新增草稿
     *
     * @param bo
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertByDraftBo(NewOrderBo bo) {
        String userId = Objects.requireNonNull(LoginHelper.getUserId()).toString();
        bo.setUserId(userId);
        BusinessOrder businessOrder = MapstructUtils.convert(bo, BusinessOrder.class);
        businessOrderMapper.insert(businessOrder);

        bo.getMerchandises().forEach(merchandise -> {
            merchandise.setOrderId(businessOrder.getId());
            orderMerchandiseService.insertByBo(merchandise);
        });
    }

    /**
     * 修改订单表
     */
    public void updateByBo(NewOrderBo bo) {
        BusinessOrder update = MapstructUtils.convert(bo, BusinessOrder.class);
        businessOrderMapper.updateById(update);
    }

    /**
     * 修改订单状态
     */
    @Transactional(rollbackFor = Exception.class)
    public void publish(NewOrderBo bo) {
        String userId = Objects.requireNonNull(LoginHelper.getUserId()).toString();
        bo.setUserId(userId);

        BusinessOrder businessOrder = MapstructUtils.convert(bo, BusinessOrder.class);

        businessOrderMapper.updateById(businessOrder);

        handleMerchandise(bo);
    }

    private void handleMerchandise(NewOrderBo bo) {
        LambdaQueryWrapper<OrderMerchandise> lqw = Wrappers.lambdaQuery();
        lqw.eq(OrderMerchandise::getOrderId, bo.getId());
        List<OrderMerchandiseVo> orderMerchandiseVos = orderMerchandiseMapper.selectVoList(lqw);

        Map<String, String> existingMerchandiseIds = orderMerchandiseVos.stream()
                .collect(Collectors.toMap(OrderMerchandiseVo::getMerchandiseId, OrderMerchandiseVo::getId));

        bo.getMerchandises().forEach(merchandise -> {
            merchandise.setOrderId(bo.getId());
            if (StringUtils.isNotBlank(existingMerchandiseIds.get(merchandise.getMerchandiseId()))) {
                merchandise.setId(existingMerchandiseIds.get(merchandise.getMerchandiseId()));
                orderMerchandiseMapper.updateById(MapstructUtils.convert(merchandise, OrderMerchandise.class));
                existingMerchandiseIds.remove(merchandise.getMerchandiseId());
            } else {
                orderMerchandiseMapper.insert(MapstructUtils.convert(merchandise, OrderMerchandise.class));
            }
        });

        orderMerchandiseMapper.deleteBatchIds(existingMerchandiseIds.values());
    }

    /**
     * 修改订单表
     */
    public void updateStatus(UpdateOrderStatusBo bo) {
        BusinessOrderVo businessOrderVo = queryById(bo.getId());
        if (businessOrderVo == null)
            throw new RuntimeException("该订单不存在！");
        if (businessOrderVo.getStatus().equals(OrderStatus.DRAFT.getCode()))
            throw new RuntimeException("不能修改为草稿状态!");
        BusinessOrder update = MapstructUtils.convert(bo, BusinessOrder.class);

        businessOrderMapper.updateById(update);

    }

    /**
     * 删除订单表
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(Collection<String> ids) {
        List<OrderMerchandiseVo> orderMerchandises = getOrderMerchandiseVos(ids);
        List<BusinessOrder> businessOrders = new ArrayList<>();

        ids.forEach(id -> {
            List<String> omIds = orderMerchandises.stream()
                    .filter(e -> e.getOrderId().equals(id))
                    .map(OrderMerchandiseVo::getId)
                    .toList();
            if (!omIds.isEmpty()) {
                orderMerchandiseService.deleteByIds(omIds);
            }

            BusinessOrder businessOrder = new BusinessOrder();
            businessOrder.setIsDelete(true);
            businessOrder.setId(id);
            businessOrders.add(businessOrder);
            refundForOrder(businessOrder);
        });

        List<String> shipmentNoticeIds = getShipmentNoticeIdByOrderIds(ids);
        if (!shipmentNoticeIds.isEmpty()) {
            shipmentNoticeService.deleteByIds(shipmentNoticeIds);
        }

        businessOrderMapper.updateBatchById(businessOrders);
    }

    private List<OrderMerchandiseVo> getOrderMerchandiseVos(Collection<String> ids) {
        LambdaQueryWrapper<OrderMerchandise> lqw = Wrappers.lambdaQuery();
        lqw.in(!ids.isEmpty(), OrderMerchandise::getOrderId, ids);
        lqw.eq(OrderMerchandise::getIsDelete, false);
        return orderMerchandiseMapper.selectVoList(lqw);
    }

    private List<String> getShipmentNoticeIdByOrderIds(Collection<String> ids) {
        LambdaQueryWrapper<ShipmentNotice> lqw = Wrappers.lambdaQuery();
        lqw.in(!ids.isEmpty(), ShipmentNotice::getOrderId, ids);
        lqw.eq(ShipmentNotice::getIsDelete, false);
        return shipmentNoticeMapper.selectVoList(lqw).stream().map(ShipmentNoticeVo::getId).toList();
    }
}
