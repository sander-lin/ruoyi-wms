package com.ruoyi.wms.service;

import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.wms.domain.vo.financial.FinancialTableInfoVo;
import com.ruoyi.wms.enums.FinancialState;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.domain.bo.financial.FinancialBo;
import com.ruoyi.wms.domain.vo.financial.FinancialVo;
import com.ruoyi.wms.domain.entity.Financial;
import com.ruoyi.wms.mapper.FinancialMapper;

import java.math.BigDecimal;
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
    public FinancialVo queryById(String id) {
        return financialMapper.selectVoById(id);
    }

    /**
     * 查询资金明细表列表
     */
    public FinancialTableInfoVo queryPageList(FinancialBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Financial> lqw = buildQueryWrapper(bo);
        Page<FinancialVo> result = financialMapper.selectVoPage(pageQuery.build(), lqw);

        FinancialTableInfoVo financialTableInfoVo = new FinancialTableInfoVo();
        BeanUtils.copyProperties(TableDataInfo.build(result), financialTableInfoVo);

        lqw.select(Financial::getAmount, Financial::getState);

        financialMapper.selectMaps(lqw, resultContext -> {
            Map<String, Object> res = resultContext.getResultObject();
            if (res.get("state").toString().equals(FinancialState.INCOME.getCode())) {
                BigDecimal income = new BigDecimal(financialTableInfoVo.getTotalIncome());
                income = income.add((BigDecimal) res.get("amount"));
                financialTableInfoVo.setTotalIncome(income.toString());

            }
            if (res.get("state").toString().equals(FinancialState.EXPENDITURE.getCode())) {
                BigDecimal expenditure = new BigDecimal(financialTableInfoVo.getTotalExpenditure());
                expenditure = expenditure.add((BigDecimal) res.get("amount"));
                financialTableInfoVo.setTotalExpenditure(expenditure.toString());
            }
        });

        return financialTableInfoVo;
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
        lqw.eq(Financial::getIsDelete, false);
        lqw.between(params.get("beginTime") != null && params.get("endTime") != null, Financial::getCreateTime,
                params.get("beginTime"), params.get("endTime"));
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
