<template>
  <div>
    <Header/>
    <div class="container clearfix">
      <el-breadcrumb style="margin-top: 10px;" separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      </el-breadcrumb>
      <div v-for="plate in plates" v-bind:key="plate.pid">
        <PlateItem class="item" :title="plate.pname" :post-count="plate.count" :to="'/plate/' + plate.pid"/>
      </div>
    </div>
  </div>
</template>

<script>
  import Header from '../components/Header'
  import PlateItem from '../components/PlateItem'
  import {getAllPlates} from '../request'

  export default {
    name: 'Home',
    components: {Header, PlateItem},
    data() {
      return {
        plates: []
      }
    },
    mounted() {
      getAllPlates().then(data => {
        this.plates = data.data
      })
    }
  }
</script>

<style scoped>
  .container {
    width: 810px;
    margin: 0 auto;
    /*padding-top: 20px;*/
    background: #fafbfc;
  }

  .item {
    float: left;
    margin: 10px;
  }
</style>
