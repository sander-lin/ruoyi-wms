package com.ruoyi.wms.service;

import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.wms.domain.entity.*;
import com.ruoyi.wms.domain.vo.OrderMerchandiseVo;
import com.ruoyi.wms.domain.bo.shipmentnotice.NewShipmentNoticeBo;
import com.ruoyi.wms.domain.bo.shipmentnotice.ShipmentNoticeMerchandiseBo;
import com.ruoyi.wms.domain.vo.ShipmentMerchandiseVo;
import com.ruoyi.wms.domain.vo.ShipmentNoticeMerchandiseVo;
import com.ruoyi.wms.domain.vo.shipment.ShipmentVo;
import com.ruoyi.wms.domain.vo.shipmentnotice.ShipmentNoticeDetailVo;
import com.ruoyi.wms.mapper.OrderMerchandiseMapper;
import com.ruoyi.wms.mapper.ShipmentMapper;
import com.ruoyi.wms.mapper.ShipmentNoticeMerchandiseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.domain.bo.ShipmentNoticeBo;
import com.ruoyi.wms.domain.vo.shipmentnotice.ShipmentNoticeVo;
import com.ruoyi.wms.mapper.ShipmentNoticeMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 发货请求通知单Service业务层处理
 *
 * @author huiwei
 * @date 2024-11-18
 */
@RequiredArgsConstructor
@Service
public class ShipmentNoticeService {

    private final ShipmentNoticeMapper shipmentNoticeMapper;
    private final ShipmentNoticeMerchandiseMapper shipmentNoticeMerchandiseMapper;
    private final OrderMerchandiseMapper orderMerchandiseMapper;
    private final ShipmentService shipmentService;
    private final ShipmentMapper shipmentMapper;

    /**
     * 查询发货请求通知单
     */
    public ShipmentNoticeDetailVo queryById(String id){
        return shipmentNoticeMapper.selectShipmentNoticeById(Long.parseLong(id));
    }


    public TableDataInfo<ShipmentNoticeVo> queryShipmentNoticeList(ShipmentNoticeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ShipmentNotice> lqw = buildQueryWrapper(bo);
        Page<ShipmentNoticeVo> result = shipmentNoticeMapper.selectShipmentNoticeVoList(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }
    /**
     * 查询发货请求通知单列表
     */
    public TableDataInfo<ShipmentNoticeVo> queryPageList(ShipmentNoticeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ShipmentNotice> lqw = buildQueryWrapper(bo);
        Page<ShipmentNoticeVo> result = shipmentNoticeMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询发货请求通知单列表
     */
    public List<ShipmentNoticeVo> queryList(ShipmentNoticeBo bo) {
        LambdaQueryWrapper<ShipmentNotice> lqw = buildQueryWrapper(bo);
        return shipmentNoticeMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ShipmentNotice> buildQueryWrapper(ShipmentNoticeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ShipmentNotice> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOrderId()), ShipmentNotice::getOrderId, bo.getOrderId());
        lqw.eq(StringUtils.isNotBlank(bo.getUserId()), ShipmentNotice::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getTag()), ShipmentNotice::getTag, bo.getTag());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), ShipmentNotice::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getDeliveryMethod()), ShipmentNotice::getDeliveryMethod, bo.getDeliveryMethod());
        return lqw;
    }

    /**
     * 新增发货请求通知单
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertByBo(NewShipmentNoticeBo bo) {
        ShipmentNotice add = MapstructUtils.convert(bo, ShipmentNotice.class);
        shipmentNoticeMapper.insert(add);

        LambdaQueryWrapper<OrderMerchandise> lqw = Wrappers.lambdaQuery();

        lqw.eq(StringUtils.isNotBlank(bo.getOrderId()),OrderMerchandise::getOrderId,bo.getOrderId());
        List<OrderMerchandiseVo> orderMerchandises = orderMerchandiseMapper.selectVoList(lqw);
        List<ShipmentNoticeMerchandiseVo> noticeMerchandise = selectNoticeMerchandiseByOrderId(bo.getOrderId());
        String shipmentNoticeId = add.getId();

        bo.getMerchandises().forEach(merchandise -> {
            merchandise.setOrderId(bo.getOrderId());
            merchandise.setShipmentNoticeId(shipmentNoticeId);

            if(!checkMerchandiseInOrder(merchandise))
                throw new IllegalArgumentException(merchandise.getMerchandiseId() + " 订单不存在该商品！");
            OrderMerchandiseVo initialVo = new OrderMerchandiseVo();
            initialVo.setQuantityRequired("0");

            int compareQuantityRequired = Integer.parseInt(orderMerchandises.stream()
                .filter(e -> e.getMerchandiseId().equals(merchandise.getMerchandiseId()))
                .findFirst()
                .orElse(initialVo)
                .getQuantityRequired());

            int compareQuantityNoticed = noticeMerchandise.stream()
                .mapToInt(e-> Integer.parseInt(e.getQuantityNotice()))
                .sum();

            if(merchandise.getQuantityNotice() > (compareQuantityRequired - compareQuantityNoticed))
                throw new IllegalArgumentException(merchandise.getMerchandiseId() + " 该商品通知发货数量不能超过需求数量！");

            shipmentNoticeMerchandiseMapper.insert(MapstructUtils.convert(merchandise, ShipmentNoticeMerchandise.class));
        });
    }

    private Boolean checkMerchandiseInOrder(ShipmentNoticeMerchandiseBo bo) {
        LambdaQueryWrapper<OrderMerchandise> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMerchandiseId()),OrderMerchandise::getMerchandiseId,bo.getMerchandiseId());
        lqw.eq(StringUtils.isNotBlank(bo.getShipmentNoticeId()),OrderMerchandise::getOrderId,bo.getOrderId());
        long count = orderMerchandiseMapper.selectCount(lqw);
        return count > 0;
    }

    public List<ShipmentNoticeMerchandiseVo> selectNoticeMerchandiseByOrderId(String id){
        LambdaQueryWrapper<ShipmentNotice> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(id),ShipmentNotice::getOrderId,id);

        List<String> ids = shipmentNoticeMapper.selectVoList(lqw).stream().map(ShipmentNoticeVo::getId).toList();

        LambdaQueryWrapper<ShipmentNoticeMerchandise> lqwSM = Wrappers.lambdaQuery();
        lqwSM.in(!ids.isEmpty(), ShipmentNoticeMerchandise::getShipmentNoticeId,ids);
        return shipmentNoticeMerchandiseMapper.selectVoList(lqwSM);
    }

    /**
     * 修改发货请求通知单
     */
    public void updateByBo(ShipmentNoticeBo bo) {
        ShipmentNotice update = MapstructUtils.convert(bo, ShipmentNotice.class);
        shipmentNoticeMapper.updateById(update);
    }

    /**
     * 批量删除发货请求通知单
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(Collection<String> ids) {
        List<String> shipmentIds = getShipmentIdByNoticeIds(ids);

        if(!shipmentIds.isEmpty()) {
            shipmentService.deleteByIds(shipmentIds);
        }

        List<ShipmentNoticeMerchandiseVo> shipmentNoticeMerchandiseVos = getShipmentNoticeMerchandiseVos(ids);
        List<ShipmentNotice> shipmentNotices = new ArrayList<>();

        ids.forEach(id -> {
            List<ShipmentNoticeMerchandise> shipmentNoticeMerchandises = shipmentNoticeMerchandiseVos.stream()
                .filter(e -> e.getShipmentNoticeId().equals(id))
                .map(e -> {
                    ShipmentNoticeMerchandise res = new ShipmentNoticeMerchandise();
                    res.setId(e.getId());
                    res.setIsDelete(true);
                    return res;
                })
                .toList();

            if(!shipmentNoticeMerchandises.isEmpty()) {
                shipmentNoticeMerchandiseMapper.updateBatchById(shipmentNoticeMerchandises);
            }

            ShipmentNotice shipmentNotice = new ShipmentNotice();
            shipmentNotice.setIsDelete(true);
            shipmentNotice.setId(id);
            shipmentNotices.add(shipmentNotice);
        });

        shipmentNoticeMapper.updateBatchById(shipmentNotices);
    }

    private List<ShipmentNoticeMerchandiseVo> getShipmentNoticeMerchandiseVos(Collection<String> ids) {
        LambdaQueryWrapper<ShipmentNoticeMerchandise> lqw = Wrappers.lambdaQuery();
        lqw.in(!ids.isEmpty(),ShipmentNoticeMerchandise::getShipmentNoticeId, ids);
        return shipmentNoticeMerchandiseMapper.selectVoList(lqw);
    }

    private List<String> getShipmentIdByNoticeIds(Collection<String> ids){
        LambdaQueryWrapper<Shipment> lqw = Wrappers.lambdaQuery();
        lqw.in(!ids.isEmpty(),Shipment::getShipmentNoticeId, ids);
        return shipmentMapper.selectVoList(lqw).stream().map(ShipmentVo::getId).toList();
    }
}
