package com.ruoyi.wms.mapper;

import com.ruoyi.common.mybatis.annotation.DataColumn;
import com.ruoyi.common.mybatis.annotation.DataPermission;
import com.ruoyi.wms.domain.entity.Financial;
import com.ruoyi.wms.domain.vo.financial.FinancialVo;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 资金明细表Mapper接口
 *
 * @author zcc
 * @date 2024-11-08
 */
@DataPermission({
    @DataColumn(key = "userName", value = "user_id")
})
public interface FinancialMapper extends BaseMapperPlus<Financial, FinancialVo> {

}
