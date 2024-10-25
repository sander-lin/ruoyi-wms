package com.ruoyi.wms.controller;

import java.util.List;

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
import com.ruoyi.wms.domain.vo.FinancesVo;
import com.ruoyi.wms.domain.bo.FinancesBo;
import com.ruoyi.wms.service.FinancesService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

/**
 * 资金明细
 *
 * @author zcc
 * @date 2024-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/finances")
public class FinancesController extends BaseController {

    private final FinancesService financesService;

    /**
     * 查询资金明细列表
     */
    @SaCheckPermission("wms:finances:list")
    @GetMapping("/list")
    public TableDataInfo<FinancesVo> list(FinancesBo bo, PageQuery pageQuery) {
        return financesService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出资金明细列表
     */
    @SaCheckPermission("wms:finances:export")
    @Log(title = "资金明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FinancesBo bo, HttpServletResponse response) {
        List<FinancesVo> list = financesService.queryList(bo);
        ExcelUtil.exportExcel(list, "资金明细", FinancesVo.class, response);
    }

    /**
     * 获取资金明细详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:finances:query")
    @GetMapping("/{id}")
    public R<FinancesVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(financesService.queryById(id));
    }

    /**
     * 新增资金明细
     */
    @SaCheckPermission("wms:finances:add")
    @Log(title = "资金明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FinancesBo bo) {
        financesService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改资金明细
     */
    @SaCheckPermission("wms:finances:edit")
    @Log(title = "资金明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FinancesBo bo) {
        financesService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除资金明细
     *
     * @param ids 主键串
     */
    @SaCheckPermission("wms:finances:remove")
    @Log(title = "资金明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        financesService.deleteByIds(List.of(ids));
        return R.ok();
    }
}
