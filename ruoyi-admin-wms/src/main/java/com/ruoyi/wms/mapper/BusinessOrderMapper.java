package com.ruoyi.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mybatis.annotation.DataColumn;
import com.ruoyi.common.mybatis.annotation.DataPermission;
import com.ruoyi.wms.domain.entity.BusinessOrder;
import com.ruoyi.wms.domain.vo.businessorder.BusinessOrderDetailVo;
import com.ruoyi.wms.domain.vo.businessorder.BusinessOrderVo;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表Mapper接口
 *
 * @author zcc
 * @date 2024-11-11
 */
@DataPermission({
    @DataColumn(key = "userName", value = "user_id")
})
public interface BusinessOrderMapper extends BaseMapperPlus<BusinessOrder, BusinessOrderVo> {
    Page<BusinessOrderVo> selectOrderList(Page<BusinessOrderVo> page, @Param(Constants.WRAPPER) Wrapper<BusinessOrder> queryWrapper);

    @DataPermission({
        @DataColumn(key = "userName", value = "o.user_id")
    })
    Page<BusinessOrderVo> queryOrderList(Page<BusinessOrderVo> page, @Param(Constants.WRAPPER) Wrapper<BusinessOrder> queryWrapper);

    List<BusinessOrderVo> selectVoBatchIds(@Param("ids") List<String> ids);
    BusinessOrderDetailVo selectOrderDetailById(@Param("id") Long id);
}
