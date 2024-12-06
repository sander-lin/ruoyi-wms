package com.ruoyi.wms.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
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
import com.ruoyi.wms.domain.vo.InventoriesVo;
import com.ruoyi.wms.domain.bo.BatchInventoriesBo;
import com.ruoyi.wms.domain.bo.InventoriesBo;
import com.ruoyi.wms.service.InventoriesService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

/**
 * 商品库存表
 *
 * @author zcc
 * @date 2024-11-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/inventories")
public class InventoriesController extends BaseController {

    private final InventoriesService inventoriesService;

    /**
     * 查询商品库存表列表
     */
    @SaCheckPermission("wms:inventories:list")
    @GetMapping("/list")
    public TableDataInfo<InventoriesVo> list(InventoriesBo bo, PageQuery pageQuery) {
        return inventoriesService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询商品库存表列表
     */
    @SaCheckPermission("wms:inventories:list")
    @GetMapping("/lists")
    public TableDataInfo<InventoriesVo> lists(InventoriesBo bo, PageQuery pageQuery) {
        return inventoriesService.queryInventoryIncludeMerchandiseList(bo, pageQuery);
    }

    /**
     * 导出商品库存表列表
     */
    @SaCheckPermission("wms:inventories:export")
    @Log(title = "商品库存表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(InventoriesBo bo, HttpServletResponse response) {
        List<InventoriesVo> list = inventoriesService.queryList(bo);
        ExcelUtil.exportExcel(list, "商品库存表", InventoriesVo.class, response);
    }

    /**
     * 获取商品库存表详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:inventories:query")
    @GetMapping("/{id}")
    public R<InventoriesVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable String id) {
        return R.ok(inventoriesService.queryById(id));
    }

    /**
     * 新增商品库存表
     */
    @SaCheckPermission("wms:inventories:add")
    @Log(title = "商品库存表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody InventoriesBo bo) {
        inventoriesService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改商品库存表
     */
    @SaCheckPermission("wms:inventories:edit")
    @Log(title = "商品库存表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody InventoriesBo bo) {
        inventoriesService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除商品库存表
     *
     * @param ids 主键串
     */
    @SaCheckPermission("wms:inventories:remove")
    @Log(title = "商品库存表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable String[] ids) {
        inventoriesService.deleteByIds(List.of(ids));
        return R.ok();
    }

    /**
     * 批量入库
     */
    @SaCheckPermission("wms:inventories:add")
    @Log(title = "商品库存表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/batchInsert")
    public R<String> batchInsert(@RequestBody BatchInventoriesBo batchInventoriesBo) {
        inventoriesService.batchInsert(batchInventoriesBo);
        return R.ok();
    }
}
