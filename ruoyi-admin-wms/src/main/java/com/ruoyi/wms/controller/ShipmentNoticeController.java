package com.ruoyi.wms.controller;

import java.util.List;

import com.ruoyi.wms.domain.bo.shipmentnotice.NewShipmentNoticeBo;
import com.ruoyi.wms.domain.vo.shipmentnotice.ShipmentNoticeDetailVo;
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
import com.ruoyi.wms.domain.vo.shipmentnotice.ShipmentNoticeVo;
import com.ruoyi.wms.domain.bo.ShipmentNoticeBo;
import com.ruoyi.wms.service.ShipmentNoticeService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

/**
 * 发货请求通知单
 *
 * @author huiwei
 * @date 2024-11-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/shipmentNotice")
public class ShipmentNoticeController extends BaseController {

    private final ShipmentNoticeService shipmentNoticeService;

    /**
     * 查询发货请求通知单列表
     */
    @SaCheckPermission("wms:shipmentNotice:list")
    @GetMapping("/list")
    public TableDataInfo<ShipmentNoticeVo> list(ShipmentNoticeBo bo, PageQuery pageQuery) {
        return shipmentNoticeService.queryShipmentNoticeList(bo, pageQuery);
    }

    /**
     * 查询发货请求通知单列表
     */
    @SaCheckPermission("wms:shipmentNotice:draftList")
    @GetMapping("/list/draft")
    public TableDataInfo<ShipmentNoticeVo> draftList(ShipmentNoticeBo bo, PageQuery pageQuery) {
        return shipmentNoticeService.queryShipmentNoticeDraftList(bo, pageQuery);
    }

    /**
     * 导出发货请求通知单列表
     */
    @SaCheckPermission("wms:shipmentNotice:export")
    @Log(title = "发货请求通知单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ShipmentNoticeBo bo, HttpServletResponse response) {
        List<ShipmentNoticeVo> list = shipmentNoticeService.queryList(bo);
        ExcelUtil.exportExcel(list, "发货请求通知单", ShipmentNoticeVo.class, response);
    }

    /**
     * 获取发货请求通知单详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:shipmentNotice:query")
    @GetMapping("/{id}")
    public R<ShipmentNoticeDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String id) {
        return R.ok(shipmentNoticeService.queryById(id));
    }

    /**
     * 新增发货请求通知单
     */
    @SaCheckPermission("wms:shipmentNotice:add")
    @Log(title = "发货请求通知单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody NewShipmentNoticeBo bo) {
        shipmentNoticeService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改发货请求通知单
     */
    @SaCheckPermission("wms:shipmentNotice:edit")
    @Log(title = "发货请求通知单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ShipmentNoticeBo bo) {
        shipmentNoticeService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除发货请求通知单
     *
     * @param ids 主键串
     */
    @SaCheckPermission("wms:shipmentNotice:remove")
    @Log(title = "发货请求通知单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        shipmentNoticeService.deleteByIds(List.of(ids));
        return R.ok();
    }
}
