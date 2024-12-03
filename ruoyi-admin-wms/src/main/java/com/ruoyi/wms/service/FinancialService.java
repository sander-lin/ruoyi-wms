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
import com.ruoyi.wms.domain.bo.financial.FinancialBo;
import com.ruoyi.wms.domain.vo.FinancialVo;
import com.ruoyi.wms.domain.entity.Financial;
import com.ruoyi.wms.mapper.FinancialMapper;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 资金明细表Service业务层处理
 *
 * @author zcc
 * @date 2024-11-08
 */
@RequiredArgsConstructor
@Service
public class FinancialService {

    private final FinancialMapper financialMapper;

    /**
     * 查询资金明细表
     */
    public FinancialVo queryById(String id){
        return financialMapper.selectVoById(id);
    }

    /**
     * 查询资金明细表列表
     */
    public TableDataInfo<FinancialVo> queryPageList(FinancialBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Financial> lqw = buildQueryWrapper(bo);
        Page<FinancialVo> result = financialMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询资金明细表列表
     */
    public List<FinancialVo> queryList(FinancialBo bo) {
        LambdaQueryWrapper<Financial> lqw = buildQueryWrapper(bo);
        return financialMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Financial> buildQueryWrapper(FinancialBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Financial> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getUserId()), Financial::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getState()), Financial::getState, bo.getState());
        lqw.eq(StringUtils.isNotBlank(bo.getAmount()), Financial::getAmount, bo.getAmount());
        lqw.eq(StringUtils.isNotBlank(bo.getEvent()), Financial::getEvent, bo.getEvent());
        lqw.eq(Financial::getIsDelete,false);
        lqw.orderByDesc(Financial::getCreateTime);
        return lqw;
    }

    /**
     * 新增资金明细表
     */
    public void insertByBo(FinancialBo bo) {
        Financial add = MapstructUtils.convert(bo, Financial.class);
        financialMapper.insert(add);
    }

    /**
     * 修改资金明细表
     */
    public void updateByBo(FinancialBo bo) {
        Financial update = MapstructUtils.convert(bo, Financial.class);
        financialMapper.updateById(update);
    }

    /**
     * 批量删除资金明细表
     */
    public void deleteByIds(Collection<String> ids) {
        financialMapper.deleteBatchIds(ids);
    }
}
