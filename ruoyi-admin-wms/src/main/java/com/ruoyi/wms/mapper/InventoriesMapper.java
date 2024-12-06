package com.ruoyi.wms.mapper;

import com.ruoyi.wms.domain.entity.Inventories;
import com.ruoyi.wms.domain.vo.InventoriesVo;
import com.ruoyi.wms.domain.bo.InventoriesBo;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import java.util.List;

/**
 * 商品库存表Mapper接口
 *
 * @author zcc
 * @date 2024-11-12
 */
public interface InventoriesMapper extends BaseMapperPlus<Inventories, InventoriesVo> {
    Page<InventoriesVo> queryInventoryIncludeMerchandiseList(Page<InventoriesVo> page,
            @Param("bo") InventoriesBo bo);

    void batchInsert(@Param("inventories") List<Inventories> inventories);

    Inventories selectByMerchandiseId(@Param("merchandiseId") String merchandiseId);

    void updateInventoryById(Inventories inventory);
}
