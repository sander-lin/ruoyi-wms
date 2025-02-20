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
import com.ruoyi.wms.domain.bo.MerchandiseOssBo;
import com.ruoyi.wms.domain.vo.MerchandiseOssVo;
import com.ruoyi.wms.domain.entity.MerchandiseOss;
import com.ruoyi.wms.mapper.MerchandiseOssMapper;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 相关商品上传文件Service业务层处理
 *
 * @author zcc
 * @date 2025-02-17
 */
@RequiredArgsConstructor
@Service
public class MerchandiseOssService {

    private final MerchandiseOssMapper merchandiseOssMapper;

    /**
     * 查询相关商品上传文件
     */
    public MerchandiseOssVo queryById(String id){
        return merchandiseOssMapper.selectVoById(id);
    }

    /**
     * 查询相关商品上传文件列表
     */
    public TableDataInfo<MerchandiseOssVo> queryPageList(MerchandiseOssBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MerchandiseOss> lqw = buildQueryWrapper(bo);
        Page<MerchandiseOssVo> result = merchandiseOssMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询相关商品上传文件列表
     */
    public List<MerchandiseOssVo> queryList(MerchandiseOssBo bo) {
        LambdaQueryWrapper<MerchandiseOss> lqw = buildQueryWrapper(bo);
        return merchandiseOssMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MerchandiseOss> buildQueryWrapper(MerchandiseOssBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MerchandiseOss> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMerchandiseId()), MerchandiseOss::getMerchandiseId, bo.getMerchandiseId());
        lqw.eq(StringUtils.isNotBlank(bo.getOssId()), MerchandiseOss::getOssId, bo.getOssId());
        return lqw;
    }

    /**
     * 新增相关商品上传文件
     */
    public void insertByBo(MerchandiseOssBo bo) {
        MerchandiseOss add = MapstructUtils.convert(bo, MerchandiseOss.class);
        merchandiseOssMapper.insert(add);
    }

    /**
     * 修改相关商品上传文件
     */
    public void updateByBo(MerchandiseOssBo bo) {
        MerchandiseOss update = MapstructUtils.convert(bo, MerchandiseOss.class);
        merchandiseOssMapper.updateById(update);
    }

    /**
     * 批量删除相关商品上传文件
     */
    public void deleteByIds(Collection<String> ids) {
        merchandiseOssMapper.deleteBatchIds(ids);
    }
}
