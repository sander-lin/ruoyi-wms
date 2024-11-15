package com.ruoyi.wms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.wms.domain.bo.BusinessOrderBo;
import com.ruoyi.wms.domain.entity.BusinessOrder;
import com.ruoyi.wms.domain.vo.BusinessOrderVo;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

/**
 * 订单表Mapper接口
 *
 * @author zcc
 * @date 2024-11-11
 */
public interface BusinessOrderMapper extends BaseMapperPlus<BusinessOrder, BusinessOrderVo> {
    Page<BusinessOrderVo> selectOrderList(Page<BusinessOrderVo> page, @Param("bo") BusinessOrderBo bo);
    int SelectOrderCount(@Param("bo") BusinessOrderBo bo);
}
