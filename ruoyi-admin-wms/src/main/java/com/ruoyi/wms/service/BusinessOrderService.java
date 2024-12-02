package com.ruoyi.wms.service;

import cn.dev33.satoken.stp.StpUtil;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.wms.domain.bo.businessorder.BusinessOrderBo;
import com.ruoyi.wms.domain.bo.businessorder.NewOrderBo;
import com.ruoyi.wms.domain.bo.businessorder.UpdateOrderBo;
import com.ruoyi.wms.domain.bo.financial.NewFinanceBo;
import com.ruoyi.wms.domain.entity.BusinessOrder;
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

import java.util.*;

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
    public BusinessOrderVo queryById(String id){
        return businessOrderMapper.selectVoById(id);
    }

    public BusinessOrderDetailVo queryOrderDetailById(String id) {
        return businessOrderMapper.selectOrderDetailById(Long.valueOf(id));
    }

    /**
     * 查询订单表列表
     */
    public TableDataInfo<BusinessOrderVo> queryPageList(BusinessOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BusinessOrder> lqw = buildQueryWrapper(bo);
        Page<BusinessOrderVo> result = businessOrderMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public TableDataInfo<BusinessOrderVo> queryOrderList(BusinessOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BusinessOrder> lqw = buildQueryWrapper(bo);
        lqw.eq(StringUtils.isNotBlank(bo.getStatus())
                && !Objects.equals(bo.getStatus(), OrderStatus.DRAFT.getCode()),
            BusinessOrder::getStatus, bo.getStatus());
        Page<BusinessOrderVo> result = businessOrderMapper.selectOrderList(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    public TableDataInfo<BusinessOrderVo> queryDraftOrderList(BusinessOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BusinessOrder> lqw = buildQueryWrapper(bo);
        lqw.eq(BusinessOrder::getStatus, OrderStatus.DRAFT.getCode());
        Page<BusinessOrderVo> result = businessOrderMapper.selectOrderList(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }



    /**
     * 查询订单表列表
     */
    public List<BusinessOrderVo> queryList(BusinessOrderBo bo) {
        LambdaQueryWrapper<BusinessOrder> lqw = buildQueryWrapper(bo);
        return businessOrderMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BusinessOrder> buildQueryWrapper(BusinessOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BusinessOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getUserId()), BusinessOrder::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), BusinessOrder::getType, bo.getType());
        lqw.eq(BusinessOrder::getIsDelete,false);
        lqw.orderByDesc(BusinessOrder::getUpdateTime);
        return lqw;
    }

    /**
     * 新增订单表
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertByBo(NewOrderBo bo) {
        String id = StpUtil.getLoginIdAsString().split(":")[1].trim();
        bo.setUserId(id);
        BusinessOrder businessOrder = MapstructUtils.convert(bo, BusinessOrder.class);

        checkAndUpdateUserBalance(businessOrder);

        businessOrder.setStatus(OrderStatus.PAID.getCode());
        businessOrderMapper.insert(businessOrder);

        bo.getMerchandises().forEach(merchandise -> {
            merchandise.setOrderId(businessOrder.getId());
            orderMerchandiseService.insertByBo(merchandise);
        });
    }

    private void checkAndUpdateUserBalance(@NotNull BusinessOrder bo) {
        NewFinanceBo newFinanceBo = new NewFinanceBo();
        newFinanceBo.setUserId(bo.getUserId());
        newFinanceBo.setState(FinancialState.EXPENDITURE.getCode());
        newFinanceBo.setEvent("订单支出： " + bo.getId());
        newFinanceBo.setAmount(bo.getTotalAmount().toString());
        userBalanceService.updateByBo(newFinanceBo);
    }

    /**
     * 新增草稿
     * @param bo
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertByDraftBo(NewOrderBo bo) {
        String id = StpUtil.getLoginIdAsString().split(":")[1].trim();
        bo.setUserId(id);
        BusinessOrder businessOrder = MapstructUtils.convert(bo, BusinessOrder.class);
        businessOrderMapper.insert(businessOrder);

        bo.getMerchandises().forEach(merchandise -> {
            merchandise.setOrderId(businessOrder.getId());
            orderMerchandiseService.insertByBo(merchandise);
        });
    }

    /**
     * 修改订单状态
     */
    public void updateStatus(NewOrderBo bo){
        String id = StpUtil.getLoginIdAsString().split(":")[1].trim();
        bo.setUserId(id);
        BusinessOrderVo businessOrderVo = queryById(bo.getId());

        BusinessOrder businessOrder = MapstructUtils.convert(bo, BusinessOrder.class);
        if(businessOrderVo.getStatus().equals(OrderStatus.DRAFT.getCode())){
            checkAndUpdateUserBalance(businessOrder);
        }
        businessOrderMapper.updateById(businessOrder);
    }

    /**
     * 修改订单表
     */
    public void updateByBo(UpdateOrderBo bo) {
        BusinessOrder update = MapstructUtils.convert(bo, BusinessOrder.class);
        businessOrderMapper.updateById(update);
    }

    /**
     * 批量删除订单表
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
            if(!omIds.isEmpty()) {
                orderMerchandiseService.deleteByIds(omIds);
            }

            BusinessOrder businessOrder = new BusinessOrder();
            businessOrder.setIsDelete(true);
            businessOrder.setId(id);
            businessOrders.add(businessOrder);
        });

        List<String> shipmentNoticeIds = getShipmentNoticeIdByOrderIds(ids);
        if(!shipmentNoticeIds.isEmpty()) {
            shipmentNoticeService.deleteByIds(shipmentNoticeIds);
        }

        businessOrderMapper.updateBatchById(businessOrders);
    }

    private List<OrderMerchandiseVo> getOrderMerchandiseVos(Collection<String> ids) {
        LambdaQueryWrapper<OrderMerchandise> lqw = Wrappers.lambdaQuery();
        lqw.in(!ids.isEmpty(),OrderMerchandise::getOrderId, ids);
        return orderMerchandiseMapper.selectVoList(lqw);
    }

    private List<String> getShipmentNoticeIdByOrderIds(Collection<String> ids){
        LambdaQueryWrapper<ShipmentNotice> lqw = Wrappers.lambdaQuery();
        lqw.in(!ids.isEmpty(),ShipmentNotice::getOrderId, ids);
        return shipmentNoticeMapper.selectVoList(lqw).stream().map(ShipmentNoticeVo::getId).toList();
    }
}
