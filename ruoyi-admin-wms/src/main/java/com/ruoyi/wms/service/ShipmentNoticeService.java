package com.ruoyi.wms.service;

import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.wms.domain.bo.shipmentnotice.NewShipmentNoticeBo;
import com.ruoyi.wms.domain.bo.shipmentnotice.ShipmentNoticeMerchandiseBo;
import com.ruoyi.wms.domain.entity.ShipmentNoticeMerchandise;
import com.ruoyi.wms.mapper.OrderMerchandiseMapper;
import com.ruoyi.wms.mapper.ShipmentNoticeMerchandiseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.domain.bo.ShipmentNoticeBo;
import com.ruoyi.wms.domain.vo.shipmentnotice.ShipmentNoticeVo;
import com.ruoyi.wms.domain.entity.ShipmentNotice;
import com.ruoyi.wms.mapper.ShipmentNoticeMapper;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 查询发货请求通知单
     */
    public ShipmentNoticeVo queryById(String id){
        return shipmentNoticeMapper.selectVoById(id);
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

        String shipmentNoticeId = add.getId();

        bo.getMerchandises().forEach(merchandise -> {
            merchandise.setOrderId(bo.getOrderId());
            merchandise.setShipmentNoticeId(shipmentNoticeId);

            if(!checkMerchandiseInOrder(merchandise)) throw new RuntimeException("订单不存在该商品！") ;

            shipmentNoticeMerchandiseMapper.insert(MapstructUtils.convert(merchandise, ShipmentNoticeMerchandise.class));
        });
    }

    private Boolean checkMerchandiseInOrder(ShipmentNoticeMerchandiseBo Bo) {
        int count = orderMerchandiseMapper.checkMerchandiseInOrder(Bo);
        return count > 0;
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
    public void deleteByIds(Collection<String> ids) {
        shipmentNoticeMapper.deleteBatchIds(ids);
    }
}
