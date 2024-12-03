package com.ruoyi.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mybatis.annotation.DataColumn;
import com.ruoyi.common.mybatis.annotation.DataPermission;
import com.ruoyi.wms.domain.entity.ShipmentNotice;
import com.ruoyi.wms.domain.vo.shipmentnotice.ShipmentNoticeDetailVo;
import com.ruoyi.wms.domain.vo.shipmentnotice.ShipmentNoticeVo;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

/**
 * 发货请求通知单Mapper接口
 *
 * @author huiwei
 * @date 2024-11-18
 */
public interface ShipmentNoticeMapper extends BaseMapperPlus<ShipmentNotice, ShipmentNoticeVo> {
    @DataPermission({
        @DataColumn(key = "userName", value = "user_id")
    })
    Page<ShipmentNoticeVo> selectShipmentNoticeVoList(Page<ShipmentNoticeVo> page, @Param(Constants.WRAPPER) Wrapper<ShipmentNotice> queryWrapper);
    @DataPermission({
        @DataColumn(key = "userName", value = "user_id")
    })
    ShipmentNoticeDetailVo selectShipmentNoticeById(@Param("id") long id);
}

