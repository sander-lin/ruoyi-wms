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
import com.ruoyi.wms.domain.vo.LogisticsVo;
import com.ruoyi.wms.domain.bo.LogisticsBo;
import com.ruoyi.wms.service.LogisticsService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

/**
 * 物流信息
 *
 * @author zcc
 * @date 2025-02-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/logistics")
public class LogisticsController extends BaseController {

    private final LogisticsService logisticsService;

    /**
     * 查询物流信息列表
     */
    @SaCheckPermission("wms:logistics:list")
    @GetMapping("/listByShipmentId/{id}")
    public TableDataInfo<LogisticsVo> list(@PathVariable String id, PageQuery pageQuery) {
        LogisticsBo bo = new LogisticsBo();
        bo.setShipmentId(id);
        return logisticsService.queryPageList(bo, pageQuery);
    }

    /**
     * 新增物流信息
     */
    @SaCheckPermission("wms:logistics:add")
    @Log(title = "物流信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LogisticsBo bo) {
        logisticsService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改物流信息
     */
    @SaCheckPermission("wms:logistics:edit")
    @Log(title = "物流信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LogisticsBo bo) {
        logisticsService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除物流信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("wms:logistics:remove")
    @Log(title = "物流信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        logisticsService.deleteByIds(List.of(ids));
        return R.ok();
    }
}
