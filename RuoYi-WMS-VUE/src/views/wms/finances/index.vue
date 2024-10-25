<template>
  <div class="app-container">
    <el-card>
      <el-form
        :model="queryParams"
        ref="queryRef"
        :inline="true"
        label-width="68px"
      >
        <el-form-item label="充值金额" prop="rechargeMoney">
          <el-input
            v-model="queryParams.rechargeMoney"
            placeholder="请输入充值金额"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="余额" prop="balance">
          <el-input
            v-model="queryParams.balance"
            placeholder="请输入余额"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="账单类型" prop="type">
          <el-select
            v-model="queryParams.type"
            placeholder="请选择账单类型"
            clearable
          >
            <el-option
              v-for="dict in wms_finances_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="账单子类型" prop="subType">
          <el-select
            v-model="queryParams.subType"
            placeholder="请选择账单子类型"
            clearable
          >
            <el-option
              v-for="dict in wms_finances_sub_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
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
          ><span style="font-size: large">资金明细</span></el-col
        >
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['wms:finances:add']"
            >新增</el-button
          >
          <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['wms:finances:export']"
            >导出</el-button
          >
        </el-col>
      </el-row>

      <el-table v-loading="loading" :data="financesList" border class="mt20">
        <el-table-column label="ID" prop="id" v-if="true" />
        <el-table-column label="充值金额" prop="rechargeMoney" />
        <el-table-column label="余额" prop="balance" />
        <el-table-column label="账单类型" prop="type">
          <template #default="scope">
            <dict-tag :options="wms_finances_type" :value="scope.row.type" />
          </template>
        </el-table-column>
        <el-table-column label="账单子类型" prop="subType">
          <template #default="scope">
            <dict-tag
              :options="wms_finances_sub_type"
              :value="scope.row.subType"
            />
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          align="right"
          class-name="small-padding fixed-width"
        >
          <template #default="scope">
            <el-button
              link
              type="primary"
              icon="Edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['wms:finances:edit']"
              >修改</el-button
            >
            <el-button
              link
              type="primary"
              icon="Delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['wms:finances:remove']"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <el-row>
        <pagination
          v-show="total > 0"
          :total="total"
          v-model:page="queryParams.pageNum"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
        />
      </el-row>
    </el-card>
    <!-- 添加或修改资金明细对话框 -->
    <el-drawer :title="title" v-model="open" size="50%" append-to-body>
      <el-form
        ref="financesRef"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item label="充值金额" prop="rechargeMoney">
          <el-input v-model="form.rechargeMoney" placeholder="请输入充值金额" />
        </el-form-item>
        <el-form-item label="余额" prop="balance">
          <el-input v-model="form.balance" placeholder="请输入余额" />
        </el-form-item>
        <el-form-item label="账单类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择账单类型">
            <el-option
              v-for="dict in wms_finances_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="账单子类型" prop="subType">
          <el-select v-model="form.subType" placeholder="请选择账单子类型">
            <el-option
              v-for="dict in wms_finances_sub_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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

<script setup name="Finances">
import {
  listFinances,
  getFinances,
  delFinances,
  addFinances,
  updateFinances,
} from "@/api/wms/finances";

const { proxy } = getCurrentInstance();
const { wms_finances_sub_type, wms_finances_type } = proxy.useDict(
  "wms_finances_sub_type",
  "wms_finances_type"
);

const financesList = ref([]);
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
    rechargeMoney: undefined,
    balance: undefined,
    type: undefined,
    subType: undefined,
  },
  rules: {
    id: [{ required: true, message: "ID不能为空", trigger: "blur" }],
    rechargeMoney: [
      { required: true, message: "充值金额不能为空", trigger: "blur" },
    ],
    balance: [{ required: true, message: "余额不能为空", trigger: "blur" }],
    type: [{ required: true, message: "账单类型不能为空", trigger: "change" }],
    subType: [
      { required: true, message: "账单子类型不能为空", trigger: "change" },
    ],
  },
});

const { queryParams, form, rules } = toRefs(data);

/** 查询资金明细列表 */
function getList() {
  loading.value = true;
  listFinances(queryParams.value).then((response) => {
    financesList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
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
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
    rechargeMoney: null,
    balance: null,
    type: null,
    subType: null,
  };
  proxy.resetForm("financesRef");
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
  title.value = "添加资金明细";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value;
  getFinances(_id).then((response) => {
    form.value = response.data;
    open.value = true;
    title.value = "修改资金明细";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["financesRef"].validate((valid) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id != null) {
        updateFinances(form.value)
          .then((response) => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          })
          .finally(() => {
            buttonLoading.value = false;
          });
      } else {
        addFinances(form.value)
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
    .confirm('是否确认删除资金明细编号为"' + _ids + '"的数据项？')
    .then(function () {
      loading.value = true;
      return delFinances(_ids);
    })
    .then(() => {
      loading.value = true;
      getList();
      proxy.$modal.msgSuccess("删除成功");
    })
    .catch(() => {})
    .finally(() => {
      loading.value = false;
    });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download(
    "wms/finances/export",
    {
      ...queryParams.value,
    },
    `finances_${new Date().getTime()}.xlsx`
  );
}

getList();
</script>
