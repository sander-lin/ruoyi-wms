package com.ruoyi.wms.service;

import com.ruoyi.common.core.utils.MapstructUtils;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.wms.domain.bo.financial.FinancialBo;
import com.ruoyi.wms.domain.bo.financial.NewFinanceBo;
import com.ruoyi.wms.enums.FinancialState;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import com.ruoyi.wms.domain.bo.financial.UserBalanceBo;
import com.ruoyi.wms.domain.vo.UserBalanceVo;
import com.ruoyi.wms.domain.entity.UserBalance;
import com.ruoyi.wms.mapper.UserBalanceMapper;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 用户余额Service业务层处理
 *
 * @author Huiwei
 * @date 2024-11-29
 */
@RequiredArgsConstructor
@Service
public class UserBalanceService {

    private final UserBalanceMapper userBalanceMapper;
    private final FinancialService financialService;

    /**
     * 查询用户余额
     */
    public UserBalanceVo queryById(String id){
        return userBalanceMapper.selectVoById(id);
    }

    /**
     * 查询用户余额列表
     */
    public TableDataInfo<UserBalanceVo> queryPageList(UserBalanceBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<UserBalance> lqw = buildQueryWrapper(bo);
        Page<UserBalanceVo> result = userBalanceMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询用户余额列表
     */
    public List<UserBalanceVo> queryList(UserBalanceBo bo) {
        LambdaQueryWrapper<UserBalance> lqw = buildQueryWrapper(bo);
        return userBalanceMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<UserBalance> buildQueryWrapper(UserBalanceBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<UserBalance> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getUserId()), UserBalance::getUserId, bo.getUserId());
        lqw.eq(bo.getBalance() != null, UserBalance::getBalance, bo.getBalance());
        return lqw;
    }

    /**
     * 新增用户余额
     */
    public void insertByBo(UserBalanceBo bo) {
        UserBalance add = MapstructUtils.convert(bo, UserBalance.class);
        userBalanceMapper.insert(add);
    }

    /**
     * 修改用户余额
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateByBo(NewFinanceBo bo) {
        UserBalance update = new UserBalance();
        update.setUserId(bo.getUserId());
        List<UserBalance> userBalances = new ArrayList<>();
        userBalances.add(update);

        LambdaQueryWrapper<UserBalance> lqw = Wrappers.lambdaQuery();
        lqw.eq(UserBalance::getUserId, bo.getUserId());
        UserBalanceVo userBalanceVo = userBalanceMapper.selectVoOne(lqw);
        if(userBalanceVo == null) {
            UserBalance userBalance = new UserBalance();
            userBalance.setUserId(bo.getUserId());
            userBalance.setBalance(new BigDecimal("0"));
            userBalanceMapper.insert(userBalance);
        } else {
            if(Objects.equals(bo.getState(), FinancialState.INCOME.getCode())) {
                update.setBalance(bo.getAmount().add(userBalanceVo.getBalance()));
            }

            if(Objects.equals(bo.getState(), FinancialState.EXPENDITURE.getCode())) {
                if(bo.getOrderId() == null) throw new RuntimeException("订单id为空");

                if( userBalanceVo.getBalance().compareTo(bo.getAmount()) < 0) {
                    throw new RuntimeException("余额不足！");
                }
                update.setBalance(userBalanceVo.getBalance().subtract(bo.getAmount()));
            }
        }

        updateFinancial(bo, userBalanceVo);

        userBalanceMapper.insertOrUpdateBatch(userBalances);
    }

    private void updateFinancial(@NotNull NewFinanceBo bo, UserBalanceVo userBalanceVo) {
        FinancialBo financialBo = new FinancialBo();
        financialBo.setUserId(bo.getUserId());
        financialBo.setAmount(bo.getAmount().toString());
        financialBo.setState(bo.getState());
        financialBo.setEvent(bo.getOrderId());
        financialBo.setLastBalance(userBalanceVo == null ? "0" : userBalanceVo.getBalance().toString());
        financialService.insertByBo(financialBo);
    }

    /**
     * 批量删除用户余额
     */
    public void deleteByIds(Collection<String> ids) {
        userBalanceMapper.deleteBatchIds(ids);
    }
}
