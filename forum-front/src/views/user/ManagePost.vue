<template>
  <div>
    <Header/>
    <div class="container">
      <div class="title clearfix">
      </div>

      <div class="text-title">
        我的帖子
      </div>
      <div class="post-items">
        <el-row class="post-item" :gutter="20">
          <el-col :span="10">
            <div class="grid-content bg-purple">标题</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">作者</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">最后修改时间</div>
          </el-col>
          <el-col :span="2">
            <div class="grid-content bg-purple">操作</div>
          </el-col>
        </el-row>

        <el-row class="post-item" :gutter="20" v-for="post in posts" :key="post.postId">
          <el-col :span="10">
            <span class="post-name" @click="toPostPage(post.postId)">{{ post.postName }}</span>
          </el-col>
          <el-col :span="4">
            <div>{{ post.user.uname }}</div>
          </el-col>
          <el-col :span="4">
            <div>{{ formatDate(post.updateDatetime) }}</div>
          </el-col>
          <el-col :span="2">
            <el-button @click="openEditor(post.postId)" size="small">编辑</el-button>
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
import Header from '../../components/Header'
import {getUserPost} from "../../request"
import {formatDate} from '../../util/date-util'
import {getToken} from "../../util/cookie-util";

export default {
  name: 'ManagePost',
  components: {Header},
  mounted() {
    this.getData(this.pagination.pageNum, this.pagination.pageSize)
  },
  data() {
    return {
      posts: [
        {
          postId: 0,
          plateId: 0,
          postName: '',
          createDatetime: 0,
          content: '',
          updateDatetime: 0,
          user: {}
        }
      ],
      pagination: {
        pageNum: 1,
        pageSize: 5,
        total: 0
      }
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    },
  },
  methods: {
    getData(pageNum, pageSize) {
      getUserPost(getToken(), pageNum, pageSize).then(data => {
        this.posts = data.data.list
        this.pagination.total = data.data.total
      })
    },
    handleCurrentChange(i) {
      this.getData(i, this.pagination.pageSize)
    },
    toPostPage(postId) {
      console.log(postId)
      this.$router.push('/post/' + postId)
    },
    formatDate(timestamp) {
      return formatDate(timestamp)
    },
    openEditor(postId) {
      this.$router.push('/update-post/' + postId)
    }
  }
}
</script>

<style scoped>
.container {
  width: 810px;
  margin: 0 auto 0;
}

.text-title {
  font-size: x-large;
  font-weight: bold;
  margin: 20px 20px 20px 10px;
}

.post-item:first-of-type {
  border-top: 1px solid #ccc;
}

.post-item {
  padding: 13px 0;
  border-bottom: 1px solid #ccc;
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
