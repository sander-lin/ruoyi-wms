package com.ruoyi.wms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.wms.domain.entity.Merchandise;
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
public interface MerchandiseMapper extends BaseMapperPlus<Merchandise, MerchandiseVo> {
    Page<MerchandiseShipmentCreatingVo> selectShipmentMerchandiseByShipmentNoticeId(Page<MerchandiseShipmentCreatingVo> page, @Param("id") long id);
    Page<MerchandiseNoticeCreatingVo> selectNoticeMerchandiseByOrderId(Page<MerchandiseNoticeCreatingVo> page, @Param("id") long id);
}
