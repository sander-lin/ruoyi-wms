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
import com.ruoyi.wms.domain.bo.LogisticsChannelBo;
import com.ruoyi.wms.domain.vo.LogisticsChannelVo;
import com.ruoyi.wms.domain.entity.LogisticsChannel;
import com.ruoyi.wms.mapper.LogisticsChannelMapper;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 物流渠道Service业务层处理
 *
 * @author zcc
 * @date 2024-11-08
 */
@RequiredArgsConstructor
@Service
public class LogisticsChannelService {

    private final LogisticsChannelMapper logisticsChannelMapper;

    /**
     * 查询物流渠道
     */
    public LogisticsChannelVo queryById(String id){
        return logisticsChannelMapper.selectVoById(id);
    }

    /**
     * 查询物流渠道列表
     */
    public TableDataInfo<LogisticsChannelVo> queryPageList(LogisticsChannelBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<LogisticsChannel> lqw = buildQueryWrapper(bo);
        Page<LogisticsChannelVo> result = logisticsChannelMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询物流渠道列表
     */
    public List<LogisticsChannelVo> queryList(LogisticsChannelBo bo) {
        LambdaQueryWrapper<LogisticsChannel> lqw = buildQueryWrapper(bo);
        return logisticsChannelMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<LogisticsChannel> buildQueryWrapper(LogisticsChannelBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<LogisticsChannel> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), LogisticsChannel::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getValue()), LogisticsChannel::getValue, bo.getValue());
        return lqw;
    }

    /**
     * 新增物流渠道
     */
    public void insertByBo(LogisticsChannelBo bo) {
        LogisticsChannel add = MapstructUtils.convert(bo, LogisticsChannel.class);
        logisticsChannelMapper.insert(add);
    }

    /**
     * 修改物流渠道
     */
    public void updateByBo(LogisticsChannelBo bo) {
        LogisticsChannel update = MapstructUtils.convert(bo, LogisticsChannel.class);
        logisticsChannelMapper.updateById(update);
    }

    /**
     * 批量删除物流渠道
     */
    public void deleteByIds(Collection<String> ids) {
        logisticsChannelMapper.deleteBatchIds(ids);
    }
}
