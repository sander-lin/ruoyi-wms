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
import com.ruoyi.wms.domain.bo.SuppliesBo;
import com.ruoyi.wms.domain.vo.SuppliesVo;
import com.ruoyi.wms.domain.entity.Supplies;
import com.ruoyi.wms.mapper.SuppliesMapper;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 供应商Service业务层处理
 *
 * @author zcc
 * @date 2025-02-19
 */
@RequiredArgsConstructor
@Service
public class SuppliesService {

    private final SuppliesMapper suppliesMapper;

    /**
     * 查询供应商
     */
    public SuppliesVo queryById(Long id){
        return suppliesMapper.selectVoById(id);
    }

    /**
     * 查询供应商列表
     */
    public TableDataInfo<SuppliesVo> queryPageList(SuppliesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Supplies> lqw = buildQueryWrapper(bo);
        Page<SuppliesVo> result = suppliesMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询供应商列表
     */
    public List<SuppliesVo> queryList(SuppliesBo bo) {
        LambdaQueryWrapper<Supplies> lqw = buildQueryWrapper(bo);
        return suppliesMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Supplies> buildQueryWrapper(SuppliesBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Supplies> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), Supplies::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getAddress()), Supplies::getAddress, bo.getAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), Supplies::getPhone, bo.getPhone());
        return lqw;
    }

    /**
     * 新增供应商
     */
    public void insertByBo(SuppliesBo bo) {
        Supplies add = MapstructUtils.convert(bo, Supplies.class);
        suppliesMapper.insert(add);
    }

    /**
     * 修改供应商
     */
    public void updateByBo(SuppliesBo bo) {
        Supplies update = MapstructUtils.convert(bo, Supplies.class);
        suppliesMapper.updateById(update);
    }

    /**
     * 批量删除供应商
     */
    public void deleteByIds(Collection<Long> ids) {
        suppliesMapper.deleteBatchIds(ids);
    }
}
