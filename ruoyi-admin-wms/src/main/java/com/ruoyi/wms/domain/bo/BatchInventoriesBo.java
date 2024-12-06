package com.ruoyi.wms.domain.bo;

import java.util.List;

public class BatchInventoriesBo {
    private List<InventoriesBo> inventories; // 批量库存信息

    // Getter 和 Setter
    public List<InventoriesBo> getInventories() {
        return inventories;
    }

    public void setInventories(List<InventoriesBo> inventories) {
        this.inventories = inventories;
    }
}
