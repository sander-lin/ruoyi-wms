import request from "@/utils/request";

// 查询物流渠道列表
export function listLogisticsPage(query) {
  return request({
    url: "/wms/logistics/list",
    method: "get",
    params: query,
  });
}

// 查询物流渠道列表
export function listLogistics(query) {
  return request({
    url: "/wms/logistics/listNoPage",
    method: "get",
    params: query,
  });
}

// 查询物流渠道详细
export function getLogistics(id) {
  return request({
    url: "/wms/logistics/" + id,
    method: "get",
  });
}

// 新增物流渠道
export function addLogistics(data) {
  return request({
    url: "/wms/logistics",
    method: "post",
    data: data,
  });
}

// 修改物流渠道
export function updateLogistics(data) {
  return request({
    url: "/wms/logistics",
    method: "put",
    data: data,
  });
}

// 删除物流渠道
export function delLogistics(id) {
  return request({
    url: "/wms/logistics/" + id,
    method: "delete",
  });
}
