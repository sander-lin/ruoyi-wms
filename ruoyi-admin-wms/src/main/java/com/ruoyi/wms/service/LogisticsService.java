package com.ruoyi.wms.service;

import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.domain.bo.LogisticsBo;
import com.ruoyi.wms.domain.vo.LogisticsVo;
import com.ruoyi.wms.domain.entity.Logistics;
import com.ruoyi.wms.domain.entity.ShipmentOrder;
import com.ruoyi.wms.mapper.LogisticsMapper;
import com.ruoyi.wms.mapper.ShipmentOrderMapper;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 物流渠道Service业务层处理
 *
 * @author zcc
 * @date 2024-10-22
 */
@RequiredArgsConstructor
@Service
public class LogisticsService {

    private final LogisticsMapper logisticsMapper;
    private final ShipmentOrderMapper shipmentOrderMapper;

    /**
     * 查询物流渠道
     */
    public LogisticsVo queryById(Long id) {
        return logisticsMapper.selectVoById(id);
    }

    /**
     * 查询物流渠道列表
     */
    public TableDataInfo<LogisticsVo> queryPageList(LogisticsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Logistics> lqw = buildQueryWrapper(bo);
        Page<LogisticsVo> result = logisticsMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询物流渠道列表
     */
    public List<LogisticsVo> queryList(LogisticsBo bo) {
        LambdaQueryWrapper<Logistics> lqw = buildQueryWrapper(bo);
        return logisticsMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Logistics> buildQueryWrapper(LogisticsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Logistics> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getLogisticsName()), Logistics::getLogisticsName, bo.getLogisticsName());
        return lqw;
    }

    /**
     * 新增物流渠道
     */
    public void insertByBo(LogisticsBo bo) {
        Logistics add = MapstructUtils.convert(bo, Logistics.class);
        logisticsMapper.insert(add);
    }

    /**
     * 修改物流渠道
     */
    public void updateByBo(LogisticsBo bo) {
        Logistics update = MapstructUtils.convert(bo, Logistics.class);
        logisticsMapper.updateById(update);
    }

    /**
     * 批量删除物流渠道
     */
    public void deleteByIds(Collection<Long> ids) {
        logisticsMapper.deleteBatchIds(ids);
    }

    /**
     * 删除物流渠道
     */
    public void deleteById(Long id) {
        validateIdBeforeDelete(id);
        logisticsMapper.deleteById(id);
    }

    private void validateIdBeforeDelete(Long id) {
        LambdaQueryWrapper<ShipmentOrder> ShipmentOrderLqw = Wrappers.lambdaQuery();
        ShipmentOrderLqw.eq(ShipmentOrder::getMerchantId, id);
        Long receiptOrderCount = shipmentOrderMapper.selectCount(ShipmentOrderLqw);
        if (receiptOrderCount != null && receiptOrderCount > 0) {
            throw new ServiceException("物流渠道已有业务关联，无法删除！", HttpStatus.CONFLICT.value());
        }
    }

}
