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
import com.ruoyi.wms.domain.vo.merchandise.MerchandiseVo;
import com.ruoyi.wms.domain.bo.MerchandiseBo;
import com.ruoyi.wms.service.MerchandiseService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

/**
 * 商品管理
 *
 * @author zcc
 * @date 2024-11-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/merchandise")
public class MerchandiseController extends BaseController {

    private final MerchandiseService merchandiseService;

    /**
     * 查询商品管理列表
     */
    @SaCheckPermission("wms:merchandise:list")
    @GetMapping("/list")
    public TableDataInfo<MerchandiseVo> list(MerchandiseBo bo, PageQuery pageQuery) {
        return merchandiseService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出商品管理列表
     */
    @SaCheckPermission("wms:merchandise:export")
    @Log(title = "商品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MerchandiseBo bo, HttpServletResponse response) {
        List<MerchandiseVo> list = merchandiseService.queryList(bo);
        ExcelUtil.exportExcel(list, "商品管理", MerchandiseVo.class, response);
    }

    /**
     * 获取商品管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:merchandise:query")
    @GetMapping("/{id}")
    public R<MerchandiseVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String id) {
        return R.ok(merchandiseService.queryById(id));
    }

    /**
     * 新增商品管理
     */
    @SaCheckPermission("wms:merchandise:add")
    @Log(title = "商品管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MerchandiseBo bo) {
        merchandiseService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改商品管理
     */
    @SaCheckPermission("wms:merchandise:edit")
    @Log(title = "商品管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MerchandiseBo bo) {
        merchandiseService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除商品管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("wms:merchandise:remove")
    @Log(title = "商品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        merchandiseService.deleteByIds(List.of(ids));
        return R.ok();
    }
}
