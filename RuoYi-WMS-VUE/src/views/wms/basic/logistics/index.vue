<template>
  <div class="app-container">
    <el-card>
      <el-form
        :model="queryParams"
        ref="queryRef"
        :inline="true"
        label-width="100px"
      >
        <el-form-item label="物流渠道名称" prop="logisticsName">
          <el-input
            v-model="queryParams.logisticsName"
            placeholder="请输入物流渠道名称"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery"
            >搜索</el-button
          >
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="mt20">
      <el-row :gutter="10" class="mb8" type="flex" justify="space-between">
        <el-col :span="6"
          ><span style="font-size: large">物流渠道列表</span></el-col
        >
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['wms:logistics:add']"
            >新增</el-button
          >
        </el-col>
      </el-row>

      <el-table
        v-loading="loading"
        :data="logisticsList"
        border
        class="mt20"
        empty-text="暂无物流渠道"
      >
        <el-table-column
          label="物流渠道名称"
          prop="logisticsName"
          align="center"
        />
        <el-table-column
          label="创建时间"
          prop="createTime"
          width="250"
          align="center"
        />
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          width="250"
        >
          <template #default="scope">
            <el-button
              link
              type="primary"
              icon="Edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['wms:logistics:edit']"
              >修改</el-button
            >
            <el-button
              link
              type="primary"
              icon="Delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['wms:logistics:remove']"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 添加或修改物流渠道对话框 -->
    <el-drawer :title="title" v-model="open" size="50%" append-to-body>
      <el-form
        ref="logisticsRef"
        :model="form"
        :rules="rules"
        label-width="120px"
      >
        <el-form-item label="物流渠道名称" prop="logisticsName">
          <el-input
            v-model="form.logisticsName"
            placeholder="请输入物流渠道名称"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" @click="submitForm"
            >确 定</el-button
          >
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-drawer>
  </div>
</template>

<script setup name="Logistics">
import {
  listLogistics,
  getLogistics,
  delLogistics,
  addLogistics,
  updateLogistics,
  listLogisticsPage,
} from "@/api/wms/logistics";
import { ElMessageBox } from "element-plus";
import { useWmsStore } from "@/store/modules/wms";

const { proxy } = getCurrentInstance();

const logisticsList = ref([]);
const open = ref(false);
const buttonLoading = ref(false);
const loading = ref(true);
const ids = ref([]);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    logisticsName: undefined,
  },
  rules: {
    id: [{ required: true, message: "不能为空", trigger: "blur" }],
    logisticsName: [
      { required: true, message: "物流渠道名称不能为空", trigger: "blur" },
    ],
  },
});

const { queryParams, form, rules } = toRefs(data);

/** 查询物流渠道列表 */
async function getList() {
  loading.value = true;
  await useWmsStore().getLogisticsList();
  let list = [...useWmsStore().logisticsList];
  if (queryParams.value.logisticsName) {
    list = list.filter(
      (it) => it.logisticsName === queryParams.value.logisticsName
    );
  }
  logisticsList.value = list;
  loading.value = false;
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    logisticsName: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
  };
  proxy.resetForm("logisticsRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加物流渠道";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value;
  getLogistics(_id).then((response) => {
    form.value = response.data;
    open.value = true;
    title.value = "修改物流渠道";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["logisticsRef"].validate((valid) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id != null) {
        updateLogistics(form.value)
          .then((response) => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          })
          .finally(() => {
            buttonLoading.value = false;
          });
      } else {
        addLogistics(form.value)
          .then((response) => {
            proxy.$modal.msgSuccess("新增成功");
            open.value = false;
            getList();
          })
          .finally(() => {
            buttonLoading.value = false;
          });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal
    .confirm("确认删除物流渠道【" + row.logisticsName + "】吗？")
    .then(function () {
      return delLogistics(_ids);
    })
    .then(() => {
      loading.value = true;
      getList();
      proxy.$modal.msgSuccess("删除成功");
    })
    .catch((e) => {
      if (e === 409) {
        return ElMessageBox.alert(
          "<div>物流渠道【" +
            row.logisticsName +
            "】已有业务数据关联，不能删除 ！</div><div>请联系管理员处理！</div>",
          "系统提示",
          {
            dangerouslyUseHTMLString: true,
          }
        );
      }
    })
    .finally(() => {
      loading.value = false;
    });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download(
    "wms/logistics/export",
    {
      ...queryParams.value,
    },
    `logistics_${new Date().getTime()}.xlsx`
  );
}

getList();
</script>
