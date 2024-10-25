<template>
  <div class="app-container home">
    <el-row class="pl20 pr20 pb20 pt20" :gutter="10">
      <el-col :span="24" class="text-center">
        <h1 class="title slide-in">仓库管理系统</h1>
      </el-col>
    </el-row>
    <el-row class="pl20 pr20" :gutter="10">
      <el-col :span="12">
        <!-- 其他内容 -->
      </el-col>
      <el-col :span="12">
        <!-- 其他内容 -->
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="Index">
const version = ref('5.2.0')

function goTarget(url) {
  window.open(url, '__blank')
}
</script>

<style scoped lang="scss">
.home {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  /* 使容器高度为视口高度 */
  text-align: center;

  .title {
    font-size: 48px;
    /* 增大字体大小 */
    font-weight: bold;
    color: #333;
    margin: 20px 0;
    opacity: 0;
    /* 初始不透明度 */
    transform: translateY(-20px);
    /* 初始位置 */
    animation: slide-in 0.5s forwards;
    /* 动画效果 */
    text-shadow: 2px 2px 10px rgba(0, 0, 0, 0.2);
    /* 添加文本阴影 */
  }

  @keyframes slide-in {
    to {
      opacity: 1;
      /* 结束时完全可见 */
      transform: translateY(0);
      /* 结束时回到原位置 */
    }
  }
}
</style>
