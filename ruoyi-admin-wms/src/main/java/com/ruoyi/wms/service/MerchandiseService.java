package com.ruoyi.wms.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.annotation.DataColumn;
import com.ruoyi.common.mybatis.annotation.DataPermission;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.vo.SysOssVo;
import com.ruoyi.system.service.SysOssService;
import com.ruoyi.wms.domain.bo.MerchandiseOssBo;
import com.ruoyi.wms.domain.entity.OrderMerchandise;
import com.ruoyi.wms.domain.vo.MerchandiseOssVo;
import com.ruoyi.wms.domain.vo.OrderMerchandiseVo;
import com.ruoyi.wms.domain.vo.merchandise.File;
import com.ruoyi.wms.domain.vo.merchandise.MerchandiseNoticeCreatingVo;
import com.ruoyi.wms.domain.vo.merchandise.MerchandiseShipmentCreatingVo;
import com.ruoyi.wms.mapper.OrderMerchandiseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.domain.bo.MerchandiseBo;
import com.ruoyi.wms.domain.vo.merchandise.MerchandiseVo;
import com.ruoyi.wms.domain.entity.Merchandise;
import com.ruoyi.wms.mapper.MerchandiseMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 商品管理Service业务层处理
 *
 * @author zcc
 * @date 2024-11-08
 */
@RequiredArgsConstructor
@Service
public class MerchandiseService {

    private final MerchandiseMapper merchandiseMapper;
    private final OrderMerchandiseMapper orderMerchandiseMapper;
    private final MerchandiseOssService merchandiseOssService;
    private final SysOssService sysOssService;

    /**
     * 查询商品管理
     */
    public MerchandiseVo queryById(String id) {
        MerchandiseVo merchandiseVo = merchandiseMapper.selectVoById(id);

        MerchandiseOssBo merchandiseOssBo = new MerchandiseOssBo();
        merchandiseOssBo.setMerchandiseId(merchandiseVo.getId());
        List<MerchandiseOssVo> merchandiseOssVos = merchandiseOssService.queryList(merchandiseOssBo);

        List<SysOssVo> sysOssVos = sysOssService.listByIds(merchandiseOssVos.stream().map(e-> Long.parseLong(e.getOssId())).toList());
        merchandiseVo.setFiles(sysOssVos.stream().map(e -> {
            File file = new File();
            file.setName(e.getOriginalName());
            file.setUrl(e.getUrl());
            return file;
        }).toList());

        return merchandiseVo;
    }

    /**
     * 通过通知单Id查询商品
     */
    public TableDataInfo<MerchandiseShipmentCreatingVo> queryByShipmentNoticeId(long id, PageQuery pageQuery) {
        Page<MerchandiseShipmentCreatingVo> result = merchandiseMapper.selectShipmentMerchandiseByShipmentNoticeId(pageQuery.build(), id);

        return TableDataInfo.build(result);
    }

    /**
     * 通过订单Id查询商品
     */
    public TableDataInfo<MerchandiseNoticeCreatingVo> queryByOrderId(long id, PageQuery pageQuery) {
        Page<MerchandiseNoticeCreatingVo> result = merchandiseMapper.selectNoticeMerchandiseByOrderId(pageQuery.build(), id);

        return TableDataInfo.build(result);
    }

    /**
     * 获取可新增库存商品列表
     */
    public TableDataInfo<MerchandiseVo> addableMerchandise(MerchandiseBo bo, PageQuery pageQuery) {
        bo.setIsConfirmed(true);
        LambdaQueryWrapper<Merchandise> lqw = buildQueryWrapper(bo);
        Page<MerchandiseVo> result = merchandiseMapper.queryAddableMerchandiseList(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询商品管理列表
     */
    @DataPermission({
        @DataColumn(value = "user_id")
    })
    public TableDataInfo<MerchandiseVo> queryPageList(MerchandiseBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Merchandise> lqw = buildQueryWrapper(bo);
        Page<MerchandiseVo> result = merchandiseMapper.queryMerchandiseList(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询商品管理列表
     */
    public List<MerchandiseVo> queryList(MerchandiseBo bo) {
        LambdaQueryWrapper<Merchandise> lqw = buildQueryWrapper(bo);
        return merchandiseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Merchandise> buildQueryWrapper(MerchandiseBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Merchandise> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getFnsku()), Merchandise::getFnsku, bo.getFnsku());
        lqw.eq(StringUtils.isNotBlank(bo.getAsin()), Merchandise::getAsin, bo.getAsin());
        lqw.like(StringUtils.isNotBlank(bo.getName()), Merchandise::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getSize()), Merchandise::getSize, bo.getSize());
        lqw.eq(StringUtils.isNotBlank(bo.getColor()), Merchandise::getColor, bo.getColor());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), Merchandise::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getImage()), Merchandise::getImage, bo.getImage());
        lqw.eq(StringUtils.isNotBlank(bo.getUserId()), Merchandise::getUserId, bo.getUserId());
        lqw.eq(bo.getIsConfirmed() != null, Merchandise::getIsConfirmed, bo.getIsConfirmed());
        lqw.eq(bo.getPrice() != null, Merchandise::getPrice, bo.getPrice());
        lqw.eq(Merchandise::getIsDelete,false);
        return lqw;
    }

    /**
     * 新增商品管理
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertByBo(MerchandiseBo bo) {

        Merchandise add = MapstructUtils.convert(bo, Merchandise.class);
        add.setIsConfirmed(false);
        merchandiseMapper.insert(add);

        if(StringUtils.isNotEmpty(bo.getFileIds())) {
            for(String ossId:bo.getFileIds().split(",")) {
                MerchandiseOssBo merchandiseOssBo = new MerchandiseOssBo();
                merchandiseOssBo.setMerchandiseId(add.getId());
                merchandiseOssBo.setOssId(ossId);
                merchandiseOssService.insertByBo(merchandiseOssBo);
            }
        }
    }

    /**
     * 修改商品管理
     */
    public void updateByBo(MerchandiseBo bo) {
        Merchandise update = MapstructUtils.convert(bo, Merchandise.class);
        merchandiseMapper.updateById(update);
    }

    /**
     * 确认商品
     */
    public void confirmById(String id){
        Merchandise update = new Merchandise();
        update.setId(id);
        update.setIsConfirmed(true);
        merchandiseMapper.updateById(update);
    }

    /**
     * 批量删除商品管理
     */
    public void deleteByIds(Collection<String> ids) {
        LambdaQueryWrapper<OrderMerchandise> lqw = Wrappers.lambdaQuery();
        lqw.in(!ids.isEmpty(), OrderMerchandise::getMerchandiseId,ids);

        List<OrderMerchandiseVo> orderMerchandiseVos = orderMerchandiseMapper.selectVoList(lqw);

        if(!orderMerchandiseVos.isEmpty()) throw new IllegalArgumentException("订单中存在将删除的商品！");

        List<Merchandise> merchandises = new ArrayList<>();
        ids.forEach(id -> {
            Merchandise merchandise = new Merchandise();
            merchandise.setId(id);
            merchandise.setIsDelete(true);
            merchandises.add(merchandise);
        });
        merchandiseMapper.updateBatchById(merchandises);

    }
}
