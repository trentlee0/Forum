<template>
  <div>
    <Header/>
    <div class="container">
      <div class="title clearfix">

        <el-breadcrumb style="margin-top: 10px;" separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{path: '/plate/' + plateId}">{{plateName}}</el-breadcrumb-item>
        </el-breadcrumb>

        <div class="text-title">
          {{ plateName }}
        </div>
        <div class="write-post">
          <el-tooltip content="点击发帖" placement="bottom">
            <el-button type="primary" icon="el-icon-edit" circle @click="toAddPost"></el-button>
          </el-tooltip>
        </div>
      </div>

      <div class="post-items">
        <el-row class="post-item" :gutter="20">
          <el-col :span="12">
            <div class="grid-content bg-purple">标题</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">作者</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">最后修改时间</div>
          </el-col>
        </el-row>

        <el-row class="post-item" :gutter="20" v-for="(post, index) in posts" :key="index">
          <el-col :span="12">
            <span class="post-name" @click="toPostPage(post.postId)">{{ post.postName }}</span>
          </el-col>
          <el-col :span="4">
            <div class="post-user">{{ post.user.uname }}</div>
          </el-col>
          <el-col :span="4">
            <div class="post-date">{{ formatDate(post.updateDatetime) }}</div>
          </el-col>
        </el-row>

        <div class="pagination">
          <el-pagination
              layout="prev, pager, next"
              :current-page="pagination.pageNum"
              :total="pagination.total" :page-size="pagination.pageSize"
              @current-change="handleCurrentChange">
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from '../components/Header'
import {getPlatePosts} from "../request"
import jutil from "jutils-src"

export default {
  name: 'Plate',
  data() {
    return {
      plateId: this.$route.params.id,
      plateName: '',
      posts: [
        {
          postId: 0,
          plateId: 0,
          postName: '',
          content: '',
          recycle: false,
          createDatetime: 0,
          updateDatetime: 0,
          user: {
            available: true,
            ugender: 0,
            ubirthday: 0,
            ugrade: 0,
            uid: 0,
            uname: ''
          },
        }
      ],
      pagination: {
        pageNum: 1,
        pageSize: 5,
        total: 0
      }
    }
  },
  mounted() {
    this.getData(this.pagination.pageNum, this.pagination.pageSize)
  },
  components: {Header},
  methods: {
    handleCurrentChange(i) {
      this.getData(i, this.pagination.pageSize)
    },
    getData(pageNum, pageSize) {
      getPlatePosts(this.plateId, pageNum, pageSize).then(data => {
        console.log(data)
        this.plateName = data.data.pname
        this.posts = data.data.posts.list
        this.pagination.total = data.data.posts.total
      })
    },
    toPostPage(postId) {
      console.log(postId)
      this.$router.push('/post/' + postId)
    },
    toAddPost() {
      this.$router.push('/addpost')
    },
    formatDate(date) {
      return jutil.formatDate(new Date(date), "YYYY-MM-DD HH:ii");
    }
  },
  computed: {}
}

</script>

<style scoped>

.container {
  width: 810px;
  margin: 0 auto;
  /*background: antiquewhite;*/
}

.text-title {
  font-size: x-large;
  font-weight: bold;
  margin: 20px;
  margin-left: 10px;
  float: left;
}

.write-post {
  float: right;
  margin-right: 10px;
  margin-top: 17px;
}

.post-user {
  font-size: 14px;
}

.post-date {
  font-size: 14px;
}

.post-item:first-of-type {
  border-top: 1px solid #698381;
  border-bottom: 1px solid #698381;
  padding-left: 10px;
  padding-right: 10px;
}

.post-item {
  border-bottom: 1px solid #ccc;
  padding: 13px 10px;
}

.post-item:hover {
  background: #ecf0f1;
}

.post-name:hover {
  cursor: pointer;
  color: #3498db;
}

.pagination {
  margin-top: 20px;;
}

</style>
