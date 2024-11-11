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
import com.ruoyi.wms.domain.bo.ShipmentBo;
import com.ruoyi.wms.domain.vo.ShipmentVo;
import com.ruoyi.wms.domain.entity.Shipment;
import com.ruoyi.wms.mapper.ShipmentMapper;

import java.util.List;
import java.util.Map;
import java.util.Collection;

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

    /**
     * 查询发货管理
     */
    public ShipmentVo queryById(String id){
        return shipmentMapper.selectVoById(id);
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
    public void insertByBo(ShipmentBo bo) {
        Shipment add = MapstructUtils.convert(bo, Shipment.class);
        shipmentMapper.insert(add);
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
