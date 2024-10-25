package com.ruoyi.wms.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.excel.utils.ExcelUtil;
import com.ruoyi.common.idempotent.annotation.RepeatSubmit;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.web.core.BaseController;
import com.ruoyi.wms.domain.bo.LogisticsBo;
import com.ruoyi.wms.domain.vo.LogisticsVo;
import com.ruoyi.wms.service.LogisticsService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物流渠道
 *
 * @author zcc
 * @date 2024-07-30
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/logistics")
public class LogisticsController extends BaseController {

    private final LogisticsService logisticsService;

    /**
     * 查询物流渠道列表
     */
    @SaCheckPermission("wms:logistics:list")
    @GetMapping("/list")
    public TableDataInfo<LogisticsVo> list(LogisticsBo bo, PageQuery pageQuery) {
        return logisticsService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询物流渠道列表
     */
    @SaCheckPermission("wms:logistics:list")
    @GetMapping("/listNoPage")
    public R<List<LogisticsVo>> listNoPage(LogisticsBo bo) {
        return R.ok(logisticsService.queryList(bo));
    }

    /**
     * 导出物流渠道列表
     */
    @SaCheckPermission("wms:logistics:list")
    @Log(title = "物流渠道", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(LogisticsBo bo, HttpServletResponse response) {
        List<LogisticsVo> list = logisticsService.queryList(bo);
        ExcelUtil.exportExcel(list, "物流渠道", LogisticsVo.class, response);
    }

    /**
     * 获取物流渠道详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:logistics:list")
    @GetMapping("/{id}")
    public R<LogisticsVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(logisticsService.queryById(id));
    }

    /**
     * 新增物流渠道
     */
    @SaCheckPermission("wms:logistics:edit")
    @Log(title = "物流渠道", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody LogisticsBo bo) {
        logisticsService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改物流渠道
     */
    @SaCheckPermission("wms:logistics:edit")
    @Log(title = "物流渠道", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody LogisticsBo bo) {
        logisticsService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除物流渠道
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:logistics:edit")
    @Log(title = "物流渠道", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        logisticsService.deleteById(id);
        return R.ok();
    }
}
