package com.ruoyi.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mybatis.annotation.DataColumn;
import com.ruoyi.common.mybatis.annotation.DataPermission;
import com.ruoyi.wms.domain.entity.BusinessOrder;
import com.ruoyi.wms.domain.entity.Merchandise;
import com.ruoyi.wms.domain.vo.businessorder.BusinessOrderVo;
import com.ruoyi.wms.domain.vo.merchandise.MerchandiseNoticeCreatingVo;
import com.ruoyi.wms.domain.vo.merchandise.MerchandiseShipmentCreatingVo;
import com.ruoyi.wms.domain.vo.merchandise.MerchandiseVo;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

/**
 * 商品管理Mapper接口
 *
 * @author zcc
 * @date 2024-11-08
 */

@DataPermission({
    @DataColumn(key = "userName", value = "user_id")
})
public interface MerchandiseMapper extends BaseMapperPlus<Merchandise, MerchandiseVo> {
    @DataPermission({
        @DataColumn(key = "userName", value = "m.user_id")
    })
    Page<MerchandiseShipmentCreatingVo> selectShipmentMerchandiseByShipmentNoticeId(Page<MerchandiseShipmentCreatingVo> page, @Param("id") long id);
    @DataPermission({
        @DataColumn(key = "userName", value = "m.user_id")
    })
    Page<MerchandiseNoticeCreatingVo> selectNoticeMerchandiseByOrderId(Page<MerchandiseNoticeCreatingVo> page, @Param("id") long id);

    @DataPermission({
        @DataColumn(key = "userName", value = "m.user_id")
    })
    Page<MerchandiseVo> queryMerchandiseList(Page<MerchandiseVo> page, @Param(Constants.WRAPPER) Wrapper<Merchandise> queryWrapper);

    Page<MerchandiseVo> queryAddableMerchandiseList(Page<MerchandiseVo> page, @Param(Constants.WRAPPER) Wrapper<Merchandise> queryWrapper);
}
