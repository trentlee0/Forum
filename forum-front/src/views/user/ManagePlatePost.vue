<template>
  <div>
    <Header/>
    <div class="container">
      <div class="title clearfix">
      </div>

      <div class="text-title">
        板块帖子
      </div>

      <div class="post-items">
        <el-row class="post-item" :gutter="20">
          <el-col :span="8">
            <div class="grid-content bg-purple">标题</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">作者</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">最后修改时间</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">操作</div>
          </el-col>
        </el-row>

        <el-row class="post-item" :gutter="20" v-for="post in posts" :key="post.postId">
          <el-col :span="8">
            <span class="post-name" @click="toPostPage(post.postId)">{{ post.postName }}</span>
          </el-col>
          <el-col :span="4">
            <div>{{ post.user.uname }}</div>
          </el-col>
          <el-col :span="4">
            <div>{{ formatDate(post.updateDatetime) }}</div>
          </el-col>
          <el-col :span="4">
            <el-button @click="openEditor(post.postId)" type="text" size="small">编辑</el-button>
            <el-button @click="remove(post.postId)" type="text" size="small">删除</el-button>
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
import {getPlatePosts, removeByPost} from "../../request"
import {formatDate} from '../../util/date-util'
import {getToken} from "../../util/cookie-util";

export default {
  name: 'ManagePlatePost',
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
          user: {uname: ''}
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
  computed: {
    user() {
      return this.$store.state.user
    },
  },
  methods: {
    getData(pageNum, pageSize) {
      getPlatePosts(this.$route.params.plateid, pageNum, pageSize).then(data => {
        console.log(data)
        this.plateName = data.data.pname
        this.posts = data.data.posts.list
        this.pagination.total = data.data.posts.total
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
    },
    remove(postId) {
      this.$confirm('此操作将永久删除该帖子, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        removeByPost(getToken(), postId).then(data => {
          console.log(data)
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
        this.$router.go(0)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
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
