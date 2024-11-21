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
import com.ruoyi.wms.domain.bo.InventoriesBo;
import com.ruoyi.wms.domain.bo.BatchInventoriesBo;
import com.ruoyi.wms.domain.vo.InventoriesVo;
import com.ruoyi.wms.domain.entity.Inventories;
import com.ruoyi.wms.mapper.InventoriesMapper;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 商品库存表Service业务层处理
 *
 * @author zcc
 * @date 2024-11-12
 */
@RequiredArgsConstructor
@Service
public class InventoriesService {

    private final InventoriesMapper inventoriesMapper;
    private final MerchandiseService merchandiseService;

    /**
     * 查询商品库存表
     */
    public InventoriesVo queryById(String id) {
        return inventoriesMapper.selectVoById(id);
    }

    /**
     * 查询商品库存表列表
     */
    public TableDataInfo<InventoriesVo> queryPageList(InventoriesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Inventories> lqw = buildQueryWrapper(bo);
        Page<InventoriesVo> result = inventoriesMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询商品库存表列表
     */
    public List<InventoriesVo> queryList(InventoriesBo bo) {
        LambdaQueryWrapper<Inventories> lqw = buildQueryWrapper(bo);
        return inventoriesMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Inventories> buildQueryWrapper(InventoriesBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Inventories> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getMerchandiseId()), Inventories::getMerchandiseId, bo.getMerchandiseId());
        if (StringUtils.isNotBlank(bo.getMerchandiseName())) {
            lqw.inSql(Inventories::getMerchandiseId,
                    "SELECT id FROM merchandise WHERE name LIKE '%" + bo.getMerchandiseName() + "%'");
        }
        return lqw;
    }

    /**
     * 新增商品库存表
     */
    public void insertByBo(InventoriesBo bo) {
        Inventories add = MapstructUtils.convert(bo, Inventories.class);
        inventoriesMapper.insert(add);
    }

    /**
     * 修改商品库存表
     */
    public void updateByBo(InventoriesBo bo) {
        Inventories update = MapstructUtils.convert(bo, Inventories.class);
        inventoriesMapper.updateById(update);
    }

    /**
     * 批量删除商品库存表
     */
    public void deleteByIds(Collection<String> ids) {
        inventoriesMapper.deleteBatchIds(ids);
    }

    /**
     * 查询库存表带商品信息
     */
    public TableDataInfo<InventoriesVo> queryInventoryIncludeMerchandiseList(InventoriesBo bo, PageQuery pageQuery) {
        Page<InventoriesVo> result = inventoriesMapper.queryInventoryIncludeMerchandiseList(pageQuery.build(), bo);
        return TableDataInfo.build(result);
    }

    /**
     * 批量入库
     */
    public void batchInsert(BatchInventoriesBo batchInventoriesBo) {
        // List<Inventories> inventories = new ArrayList<>();
        // for (InventoriesBo inventoryBo : batchInventoriesBo.getInventories()) {
        // Inventories inventory = MapstructUtils.convert(inventoryBo,
        // Inventories.class);
        // inventories.add(inventory);
        // }
        // inventoriesMapper.batchInsert(inventories); // 调用 Mapper 批量插入

        for (InventoriesBo inventoryBo : batchInventoriesBo.getInventories()) {
            // 检查数据库中是否已存在相同的 merchandiseId
            Inventories existingInventory = inventoriesMapper.selectByMerchandiseId(inventoryBo.getMerchandiseId());

            if (existingInventory != null) {
                // 如果存在，更新数量
                System.out.println("xxxx: " + existingInventory.getNumber() + " + " + inventoryBo.getNumber());
                existingInventory.setNumber(existingInventory.getNumber() +
                        Integer.valueOf(inventoryBo.getNumber()));
                if (inventoryBo.getUnit() != null && !inventoryBo.getUnit().isEmpty()) {
                    existingInventory.setUnit(inventoryBo.getUnit());
                }
                if (inventoryBo.getRemark() != null && !inventoryBo.getRemark().isEmpty()) {
                    existingInventory.setRemark(inventoryBo.getRemark());
                }
                inventoriesMapper.updateInventoryById(existingInventory);
            } else {
                // 如果不存在，插入新纪录
                Inventories newInventory = MapstructUtils.convert(inventoryBo,
                        Inventories.class);
                inventoriesMapper.insert(newInventory);
            }
        }
    }
}
