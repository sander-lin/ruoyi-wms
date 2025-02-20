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
import com.ruoyi.wms.domain.bo.LogisticsBo;
import com.ruoyi.wms.domain.vo.LogisticsVo;
import com.ruoyi.wms.domain.entity.Logistics;
import com.ruoyi.wms.mapper.LogisticsMapper;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 物流信息Service业务层处理
 *
 * @author zcc
 * @date 2025-02-20
 */
@RequiredArgsConstructor
@Service
public class LogisticsService {

    private final LogisticsMapper logisticsMapper;

    /**
     * 查询物流信息
     */
    public LogisticsVo queryById(String id){
        return logisticsMapper.selectVoById(id);
    }

    /**
     * 查询物流信息列表
     */
    public TableDataInfo<LogisticsVo> queryPageList(LogisticsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Logistics> lqw = buildQueryWrapper(bo);
        Page<LogisticsVo> result = logisticsMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询物流信息列表
     */
    public List<LogisticsVo> queryList(LogisticsBo bo) {
        LambdaQueryWrapper<Logistics> lqw = buildQueryWrapper(bo);
        return logisticsMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Logistics> buildQueryWrapper(LogisticsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Logistics> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getShipmentId()), Logistics::getShipmentId, bo.getShipmentId());
        lqw.eq(StringUtils.isNotBlank(bo.getLogisticsInfo()), Logistics::getLogisticsInfo, bo.getLogisticsInfo());
        lqw.eq(bo.getLogisticsDate() != null, Logistics::getLogisticsDate, bo.getLogisticsDate());
        return lqw;
    }

    /**
     * 新增物流信息
     */
    public void insertByBo(LogisticsBo bo) {
        Logistics add = MapstructUtils.convert(bo, Logistics.class);
        logisticsMapper.insert(add);
    }

    /**
     * 修改物流信息
     */
    public void updateByBo(LogisticsBo bo) {
        Logistics update = MapstructUtils.convert(bo, Logistics.class);
        logisticsMapper.updateById(update);
    }

    /**
     * 批量删除物流信息
     */
    public void deleteByIds(Collection<String> ids) {
        logisticsMapper.deleteBatchIds(ids);
    }
}
