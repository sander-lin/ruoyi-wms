package com.ruoyi.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mybatis.annotation.DataColumn;
import com.ruoyi.common.mybatis.annotation.DataPermission;
import com.ruoyi.wms.domain.bo.businessorder.BusinessOrderBo;
import com.ruoyi.wms.domain.entity.BusinessOrder;
import com.ruoyi.wms.domain.entity.ShipmentNotice;
import com.ruoyi.wms.domain.vo.businessorder.BusinessOrderDetailVo;
import com.ruoyi.wms.domain.vo.businessorder.BusinessOrderVo;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

/**
 * 订单表Mapper接口
 *
 * @author zcc
 * @date 2024-11-11
 */
public interface BusinessOrderMapper extends BaseMapperPlus<BusinessOrder, BusinessOrderVo> {
    @DataPermission({
        @DataColumn(key = "userName", value = "user_id")
    })
    Page<BusinessOrderVo> selectOrderList(Page<BusinessOrderVo> page,@Param(Constants.WRAPPER) Wrapper<BusinessOrder> queryWrapper);
    @DataPermission({
        @DataColumn(key = "userName", value = "user_id")
    })
    BusinessOrderDetailVo selectOrderDetailById(@Param("id") Long id);
}
