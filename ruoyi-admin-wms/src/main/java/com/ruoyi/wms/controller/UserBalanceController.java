package com.ruoyi.wms.controller;

import java.util.List;

import com.ruoyi.wms.domain.bo.financial.NewFinanceBo;
import com.ruoyi.wms.domain.bo.financial.NewUserBalanceBo;
import com.ruoyi.wms.enums.FinancialState;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.idempotent.annotation.RepeatSubmit;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.web.core.BaseController;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.excel.utils.ExcelUtil;
import com.ruoyi.wms.domain.vo.UserBalanceVo;
import com.ruoyi.wms.domain.bo.financial.UserBalanceBo;
import com.ruoyi.wms.service.UserBalanceService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

/**
 * 用户余额
 *
 * @author Huiwei
 * @date 2024-11-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/balance")
public class UserBalanceController extends BaseController {

    private final UserBalanceService userBalanceService;

    /**
     * 查询用户余额列表
     */
    @SaCheckPermission("wms:balance:list")
    @GetMapping("/list")
    public TableDataInfo<UserBalanceVo> list(UserBalanceBo bo, PageQuery pageQuery) {
        return userBalanceService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户余额列表
     */
    @SaCheckPermission("wms:balance:export")
    @Log(title = "用户余额", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(UserBalanceBo bo, HttpServletResponse response) {
        List<UserBalanceVo> list = userBalanceService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户余额", UserBalanceVo.class, response);
    }

    /**
     * 获取用户余额详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:balance:query")
    @GetMapping("/{id}")
    public R<UserBalanceVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String id) {
        return R.ok(userBalanceService.queryById(id));
    }

    /**
     * 新增用户余额
     */
    @SaCheckPermission("wms:balance:add")
    @Log(title = "用户余额", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody UserBalanceBo bo) {
        userBalanceService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改用户余额
     */
    @SaCheckPermission("wms:balance:edit")
    @Log(title = "用户余额", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody NewUserBalanceBo bo) {
        NewFinanceBo newFinanceBo = new NewFinanceBo();
        newFinanceBo.setAmount(bo.getAmount());
        newFinanceBo.setState(FinancialState.INCOME.getCode());

        userBalanceService.updateByBo(newFinanceBo);
        return R.ok();
    }

    /**
     * 删除用户余额
     *
     * @param ids 主键串
     */
    @SaCheckPermission("wms:balance:remove")
    @Log(title = "用户余额", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        userBalanceService.deleteByIds(List.of(ids));
        return R.ok();
    }
}
