<template>
  <div>
    <h1 class="title center">404</h1>
    <p class="center"><el-button type="text" @click="$router.push('/')">回首页</el-button></p>
  </div>
</template>

<script>
export default {
  name: "NotFound"
}
</script>

<style scoped>
.center {
  text-align: center;
}

.title {
  font-size: 80px;
}
</style>
