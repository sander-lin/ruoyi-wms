package com.ruoyi.wms.service;

import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.wms.domain.bo.ShipmentNoticeBo;
import com.ruoyi.wms.domain.bo.shipment.NewShipmentBo;
import com.ruoyi.wms.domain.bo.shipment.ShipmentMerchandiseBo;
import com.ruoyi.wms.domain.entity.*;
import com.ruoyi.wms.domain.vo.ShipmentMerchandiseVo;
import com.ruoyi.wms.domain.vo.ShipmentNoticeMerchandiseVo;
import com.ruoyi.wms.domain.vo.shipment.ShipmentDetailVo;
import com.ruoyi.wms.domain.vo.shipmentnotice.ShipmentNoticeDetailVo;
import com.ruoyi.wms.mapper.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.domain.bo.shipment.ShipmentBo;
import com.ruoyi.wms.domain.vo.shipment.ShipmentVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 发货管理Service业务层处理
 *
 * @author zcc
 * @date 2024-11-08
 */
@RequiredArgsConstructor
@Service
public class ShipmentService {

    private final ShipmentMapper shipmentMapper;
    private final InventoriesMapper inventoriesMapper;
    private final ShipmentMerchandiseMapper shipmentMerchandiseMapper;
    private final ShipmentNoticeMapper shipmentNoticeMapper;
    private final ShipmentNoticeMerchandiseMapper shipmentNoticeMerchandiseMapper;

    /**
     * 查询发货管理
     */
    public ShipmentDetailVo queryById(String id){
        return shipmentMapper.selectShipmentById(Long.parseLong(id));
    }

    /**
     * 查询发货管理列表
     */
    public TableDataInfo<ShipmentVo> queryPageList(ShipmentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Shipment> lqw = buildQueryWrapper(bo);
        Page<ShipmentVo> result = shipmentMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询发货管理列表
     */
    public List<ShipmentVo> queryList(ShipmentBo bo) {
        LambdaQueryWrapper<Shipment> lqw = buildQueryWrapper(bo);
        return shipmentMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Shipment> buildQueryWrapper(ShipmentBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Shipment> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getShipmentNoticeId()), Shipment::getShipmentNoticeId, bo.getShipmentNoticeId());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), Shipment::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getDeliveryMethod()), Shipment::getDeliveryMethod, bo.getDeliveryMethod());
        lqw.eq(StringUtils.isNotBlank(bo.getLogisticsNumber()), Shipment::getLogisticsNumber, bo.getLogisticsNumber());
        return lqw;
    }

    /**
     * 新增发货管理
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertByBo(NewShipmentBo bo) {
        Shipment add = MapstructUtils.convert(bo, Shipment.class);
        shipmentMapper.insert(add);
        LambdaQueryWrapper<ShipmentNoticeMerchandise> lqw = Wrappers.lambdaQuery();

        lqw.eq(StringUtils.isNotBlank(bo.getShipmentNoticeId()),
            ShipmentNoticeMerchandise::getShipmentNoticeId, bo.getShipmentNoticeId());

        List<ShipmentNoticeMerchandiseVo> shipmentNoticeMerchandises = shipmentNoticeMerchandiseMapper.selectVoList(lqw);
        List<ShipmentMerchandiseVo> shipmentMerchandises = selectShipmentMerchandiseByShipmentNoticeId(bo.getShipmentNoticeId());

        bo.getMerchandises().forEach(merchandise -> {
            Inventories inventory = inventoriesMapper.selectByMerchandiseId(merchandise.getMerchandiseId());

            if (inventory == null)
                throw new IllegalArgumentException(merchandise.getMerchandiseId() +" 库存中不存在该商品！");

            ShipmentNoticeMerchandiseVo initialVo = new ShipmentNoticeMerchandiseVo();
            initialVo.setQuantityNotice("0");

            int compareQuantityNotice = Integer.parseInt(shipmentNoticeMerchandises.stream()
                .filter(e-> e.getMerchandiseId().equals(merchandise.getMerchandiseId()))
                .findFirst().orElse(initialVo).getQuantityNotice());

            int compareQuantityShipped = shipmentMerchandises.stream()
                .filter(e -> e.getMerchandiseId().equals(merchandise.getMerchandiseId()))
                .mapToInt(e -> Integer.parseInt(e.getQuantityShipped()))
                .sum();

            if (merchandise.getQuantityShipped() > (compareQuantityNotice - compareQuantityShipped)) {
                throw new IllegalArgumentException(merchandise.getMerchandiseId() + " 该商品发货数量不能超过通知发货数量！");
            }
            if (inventory.getNumber() < merchandise.getQuantityShipped()){
                throw new IllegalArgumentException(merchandise.getMerchandiseId() + " 该商品库存不足！");
            }

            merchandise.setShipmentId(add.getId());
            merchandise.setShipmentNoticeId(bo.getShipmentNoticeId());
            if(!checkMerchandiseInShipmentNotice(merchandise))
                throw new RuntimeException(merchandise.getMerchandiseId() + " 通知单不存在该商品！");

            shipmentMerchandiseMapper.insert(MapstructUtils.convert(merchandise, ShipmentMerchandise.class));

            inventory.setNumber(inventory.getNumber() - merchandise.getQuantityShipped());
            inventoriesMapper.updateInventoryById(inventory);
        });

        checkAndSetShipmentNoticeStatus(bo);
    }

    public List<ShipmentMerchandiseVo> selectShipmentMerchandiseByShipmentNoticeId(String id) {

        LambdaQueryWrapper<Shipment> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(id),Shipment::getShipmentNoticeId,id);

        List<String> ids = shipmentMapper.selectVoList(lqw).stream().map(ShipmentVo::getId).toList();

        LambdaQueryWrapper<ShipmentMerchandise> lqwSM = Wrappers.lambdaQuery();
        lqwSM.in(!ids.isEmpty(), ShipmentMerchandise::getShipmentId,ids);
        return shipmentMerchandiseMapper.selectVoList(lqwSM);
    }

    private void checkAndSetShipmentNoticeStatus(NewShipmentBo bo){
        ShipmentNoticeDetailVo shipmentNotice = shipmentNoticeMapper
            .selectShipmentNoticeById(Long.parseLong(bo.getShipmentNoticeId()));

        int shipmentNoticeSum = shipmentNotice.getMerchandises().stream()
            .mapToInt(merchandise -> Integer.parseInt(merchandise.getQuantityNotice().trim()))
            .sum();

        List<Long> ids = shipmentNotice.getShipments()
            .stream()
            .map(shipment -> Long.parseLong(shipment.getId()))
            .toList();
        LambdaQueryWrapper<ShipmentMerchandise> lqw = Wrappers.lambdaQuery();
        lqw.in(!ids.isEmpty(), ShipmentMerchandise::getShipmentId,ids);
        List<ShipmentMerchandiseVo> shipmentMerchandiseVos = shipmentMerchandiseMapper.selectVoList(lqw);

        int shipmentShippedSum = shipmentMerchandiseVos.stream()
            .mapToInt(
                shipmentMerchandiseVo -> Integer.parseInt(shipmentMerchandiseVo.getQuantityShipped().trim())
            )
            .sum();

        ShipmentNoticeBo shipmentNoticeBo = new ShipmentNoticeBo();
        shipmentNoticeBo.setId(shipmentNotice.getId());

        if (shipmentNoticeSum > shipmentShippedSum && shipmentShippedSum > 0) {
            shipmentNoticeBo.setStatus(ShipmentNoticeStatus.PART_SHIPPED.getCode());
            shipmentNoticeMapper.updateById(MapstructUtils.convert(shipmentNoticeBo,ShipmentNotice.class));
        }

        shipmentNoticeBo.setStatus(ShipmentNoticeStatus.ALL_SHIPPED.getCode());
        shipmentNoticeMapper.updateById(MapstructUtils.convert(shipmentNoticeBo,ShipmentNotice.class));
    }

    private Boolean checkMerchandiseInShipmentNotice(ShipmentMerchandiseBo bo) {
        LambdaQueryWrapper<ShipmentNoticeMerchandise> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMerchandiseId()),
            ShipmentNoticeMerchandise::getMerchandiseId,bo.getMerchandiseId());
        lqw.eq(StringUtils.isNotBlank(bo.getShipmentNoticeId()),
            ShipmentNoticeMerchandise::getShipmentNoticeId,bo.getShipmentNoticeId());
        long count = shipmentNoticeMerchandiseMapper.selectCount(lqw);
        return count > 0;
    }

    @Getter
    private enum ShipmentNoticeStatus {
        PENDING("未发货", "1"),
        PART_SHIPPED("部分发货", "2"),
        ALL_SHIPPED("全部发货", "3"),
        COMPLETED("已完成", "4"),
        CLOSED("已关闭", "5");

        // 获取枚举常量的描述
        private final String description;
        // 获取枚举常量的代码
        private final String code;

        // 构造方法，设置每个枚举常量的描述和代码
        ShipmentNoticeStatus(String description, String code) {
            this.description = description;
            this.code = code;
        }
    }

    /**
     * 修改发货管理
     */
    public void updateByBo(ShipmentBo bo) {
        Shipment update = MapstructUtils.convert(bo, Shipment.class);
        shipmentMapper.updateById(update);
    }

    /**
     * 批量删除发货管理
     */
    public void deleteByIds(Collection<String> ids) {
        shipmentMapper.deleteBatchIds(ids);
    }
}
