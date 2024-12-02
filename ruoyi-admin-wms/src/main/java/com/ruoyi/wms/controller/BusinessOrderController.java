package com.ruoyi.wms.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaIgnore;
import com.ruoyi.wms.domain.bo.businessorder.BusinessOrderBo;
import com.ruoyi.wms.domain.bo.businessorder.NewOrderBo;
import com.ruoyi.wms.domain.bo.businessorder.UpdateOrderBo;
import com.ruoyi.wms.domain.vo.businessorder.BusinessOrderDetailVo;
import com.ruoyi.wms.domain.vo.businessorder.BusinessOrderVo;
import com.ruoyi.wms.service.BusinessOrderService;
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
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

/**
 * 订单表
 *
 * @author zcc
 * @date 2024-11-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/order")
public class BusinessOrderController extends BaseController {

    private final BusinessOrderService orderService;

    /**
     * 查询订单表列表
     */
    @SaCheckPermission("wms:order:list")
    @GetMapping("/list")
    public TableDataInfo<BusinessOrderVo> lists(BusinessOrderBo bo, PageQuery pageQuery) {
        return orderService.queryOrderList(bo, pageQuery);
    }

    /**
     * 查询草稿订单表列表
     */
    @SaCheckPermission("wms:order:draftList")
    @GetMapping("/list/draft")
    public TableDataInfo<BusinessOrderVo> draftLists(BusinessOrderBo bo, PageQuery pageQuery) {
        return orderService.queryDraftOrderList(bo, pageQuery);
    }

    /**
     * 获取订单表详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:order:query")
    @GetMapping("/{id}")
    public R<BusinessOrderDetailVo> queryDetailById(@NotNull(message = "id不能为空") @PathVariable String id){
        return R.ok(orderService.queryOrderDetailById(id));
    }

    /**
     * 导出订单表列表
     */
    @SaCheckPermission("wms:order:export")
    @Log(title = "订单表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BusinessOrderBo bo, HttpServletResponse response) {
        List<BusinessOrderVo> list = orderService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单表", BusinessOrderVo.class, response);
    }


    /**
     * 新增订单表
     */
    @SaCheckPermission("wms:order:add")
    @Log(title = "订单表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody NewOrderBo bo) {
        orderService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 新增草稿订单表
     */
    @SaCheckPermission("wms:order:addDraft")
    @Log(title = "订单表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/draft")
    public R<Void> addDraft(@Validated(AddGroup.class) @RequestBody NewOrderBo bo) {
        orderService.insertByDraftBo(bo);
        return R.ok();
    }

    /**
     * 修改订单表
     */
    @SaCheckPermission("wms:order:edit")
    @Log(title = "订单表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody UpdateOrderBo bo) {
        orderService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除订单表
     *
     * @param ids 主键串
     */
    @SaCheckPermission("wms:order:remove")
    @Log(title = "订单表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        orderService.deleteByIds(List.of(ids));
        return R.ok();
    }
}
