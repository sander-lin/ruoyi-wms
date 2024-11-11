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
import com.ruoyi.wms.domain.vo.LogisticsChannelVo;
import com.ruoyi.wms.domain.bo.LogisticsChannelBo;
import com.ruoyi.wms.service.LogisticsChannelService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

/**
 * 物流渠道
 *
 * @author zcc
 * @date 2024-11-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/channel")
public class LogisticsChannelController extends BaseController {

    private final LogisticsChannelService logisticsChannelService;

    /**
     * 查询物流渠道列表
     */
    @SaCheckPermission("wms:channel:list")
    @GetMapping("/list")
    public TableDataInfo<LogisticsChannelVo> list(LogisticsChannelBo bo, PageQuery pageQuery) {
        return logisticsChannelService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出物流渠道列表
     */
    @SaCheckPermission("wms:channel:export")
    @Log(title = "物流渠道", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(LogisticsChannelBo bo, HttpServletResponse response) {
        List<LogisticsChannelVo> list = logisticsChannelService.queryList(bo);
        ExcelUtil.exportExcel(list, "物流渠道", LogisticsChannelVo.class, response);
    }

    /**
     * 获取物流渠道详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:channel:query")
    @GetMapping("/{id}")
    public R<LogisticsChannelVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String id) {
        return R.ok(logisticsChannelService.queryById(id));
    }

    /**
     * 新增物流渠道
     */
    @SaCheckPermission("wms:channel:add")
    @Log(title = "物流渠道", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LogisticsChannelBo bo) {
        logisticsChannelService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改物流渠道
     */
    @SaCheckPermission("wms:channel:edit")
    @Log(title = "物流渠道", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LogisticsChannelBo bo) {
        logisticsChannelService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除物流渠道
     *
     * @param ids 主键串
     */
    @SaCheckPermission("wms:channel:remove")
    @Log(title = "物流渠道", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        logisticsChannelService.deleteByIds(List.of(ids));
        return R.ok();
    }
}
