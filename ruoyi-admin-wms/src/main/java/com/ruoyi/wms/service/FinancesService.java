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
import com.ruoyi.wms.domain.bo.FinancesBo;
import com.ruoyi.wms.domain.vo.FinancesVo;
import com.ruoyi.wms.domain.entity.Finances;
import com.ruoyi.wms.mapper.FinancesMapper;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 资金明细Service业务层处理
 *
 * @author zcc
 * @date 2024-10-24
 */
@RequiredArgsConstructor
@Service
public class FinancesService {

    private final FinancesMapper financesMapper;

    /**
     * 查询资金明细
     */
    public FinancesVo queryById(Long id){
        return financesMapper.selectVoById(id);
    }

    /**
     * 查询资金明细列表
     */
    public TableDataInfo<FinancesVo> queryPageList(FinancesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Finances> lqw = buildQueryWrapper(bo);
        Page<FinancesVo> result = financesMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询资金明细列表
     */
    public List<FinancesVo> queryList(FinancesBo bo) {
        LambdaQueryWrapper<Finances> lqw = buildQueryWrapper(bo);
        return financesMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Finances> buildQueryWrapper(FinancesBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Finances> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getRechargeMoney() != null, Finances::getRechargeMoney, bo.getRechargeMoney());
        lqw.eq(bo.getBalance() != null, Finances::getBalance, bo.getBalance());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), Finances::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getSubType()), Finances::getSubType, bo.getSubType());
        return lqw;
    }

    /**
     * 新增资金明细
     */
    public void insertByBo(FinancesBo bo) {
        Finances add = MapstructUtils.convert(bo, Finances.class);
        financesMapper.insert(add);
    }

    /**
     * 修改资金明细
     */
    public void updateByBo(FinancesBo bo) {
        Finances update = MapstructUtils.convert(bo, Finances.class);
        financesMapper.updateById(update);
    }

    /**
     * 批量删除资金明细
     */
    public void deleteByIds(Collection<Long> ids) {
        financesMapper.deleteBatchIds(ids);
    }
}
