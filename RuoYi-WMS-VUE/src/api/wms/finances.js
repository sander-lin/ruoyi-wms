import request from "@/utils/request";

// 查询资金明细列表
export function listFinances(query) {
  return request({
    url: "/wms/finances/list",
    method: "get",
    params: query,
  });
}

// 查询资金明细详细
export function getFinances(id) {
  return request({
    url: "/wms/finances/" + id,
    method: "get",
  });
}

// 新增资金明细
export function addFinances(data) {
  return request({
    url: "/wms/finances",
    method: "post",
    data: data,
  });
}

// 修改资金明细
export function updateFinances(data) {
  return request({
    url: "/wms/finances",
    method: "put",
    data: data,
  });
}

// 删除资金明细
export function delFinances(id) {
  return request({
    url: "/wms/finances/" + id,
    method: "delete",
  });
}
