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
import com.ruoyi.wms.domain.vo.SuppliesVo;
import com.ruoyi.wms.domain.bo.SuppliesBo;
import com.ruoyi.wms.service.SuppliesService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

/**
 * 供应商
 *
 * @author zcc
 * @date 2025-02-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/supplies")
public class SuppliesController extends BaseController {

    private final SuppliesService suppliesService;

    /**
     * 查询供应商列表
     */
    @SaCheckPermission("wms:supplies:list")
    @GetMapping("/list")
    public TableDataInfo<SuppliesVo> list(SuppliesBo bo, PageQuery pageQuery) {
        return suppliesService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出供应商列表
     */
    @SaCheckPermission("wms:supplies:export")
    @Log(title = "供应商", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SuppliesBo bo, HttpServletResponse response) {
        List<SuppliesVo> list = suppliesService.queryList(bo);
        ExcelUtil.exportExcel(list, "供应商", SuppliesVo.class, response);
    }

    /**
     * 获取供应商详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:supplies:query")
    @GetMapping("/{id}")
    public R<SuppliesVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(suppliesService.queryById(id));
    }

    /**
     * 新增供应商
     */
    @SaCheckPermission("wms:supplies:add")
    @Log(title = "供应商", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SuppliesBo bo) {
        suppliesService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改供应商
     */
    @SaCheckPermission("wms:supplies:edit")
    @Log(title = "供应商", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SuppliesBo bo) {
        suppliesService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除供应商
     *
     * @param ids 主键串
     */
    @SaCheckPermission("wms:supplies:remove")
    @Log(title = "供应商", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        suppliesService.deleteByIds(List.of(ids));
        return R.ok();
    }
}
