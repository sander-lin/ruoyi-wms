package com.ruoyi.wms.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.web.core.BaseController;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.excel.utils.ExcelUtil;
import com.ruoyi.wms.domain.vo.FinancialVo;
import com.ruoyi.wms.domain.bo.financial.FinancialBo;
import com.ruoyi.wms.service.FinancialService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

/**
 * 资金明细表
 *
 * @author zcc
 * @date 2024-11-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/financial")
public class FinancialController extends BaseController {

    private final FinancialService financialService;

    /**
     * 查询资金明细表列表
     */
    @SaCheckPermission("wms:financial:list")
    @GetMapping("/list")
    public TableDataInfo<FinancialVo> list(FinancialBo bo, PageQuery pageQuery) {
        return financialService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出资金明细表列表
     */
    @SaCheckPermission("wms:financial:export")
    @Log(title = "资金明细表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FinancialBo bo, HttpServletResponse response) {
        List<FinancialVo> list = financialService.queryList(bo);
        ExcelUtil.exportExcel(list, "资金明细表", FinancialVo.class, response);
    }

    /**
     * 获取资金明细表详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:financial:query")
    @GetMapping("/{id}")
    public R<FinancialVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String id) {
        return R.ok(financialService.queryById(id));
    }

    /**
     * 删除资金明细表
     *
     * @param ids 主键串
     */
    @SaCheckPermission("wms:financial:remove")
    @Log(title = "资金明细表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        financialService.deleteByIds(List.of(ids));
        return R.ok();
    }
}
