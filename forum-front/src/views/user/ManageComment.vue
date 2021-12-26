<template>
  <div>
    <div>
      <Header/>
    </div>
    <div class="container clearfix">
      <div class="text-title">
        回帖管理
      </div>
      <div class="clearfix"></div>
      <div class="post-items">
        <el-row class="post-item" :gutter="20">
          <el-col :span="8">
            <div class="grid-content bg-purple">评论ID</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">内容</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">发表时间</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">操作</div>
          </el-col>
        </el-row>

        <el-row class="post-item" :gutter="20" v-for="(comment, index) in list" :key="index">
          <el-col :span="8">
            <span class="post-name">{{ comment.cid }}</span>
          </el-col>
          <el-col :span="4">
            <div class="post-user comment-content" @click="getPost(comment.postId)">{{ comment.text }}</div>
          </el-col>
          <el-col :span="4">
            <div class="post-user">{{ formatDate(comment.publishDatetime) }}</div>
          </el-col>
          <el-col :span="4">
            <el-button
                type="text"
                size="small" @click="removeComment(comment.cid, index)">
              删除
            </el-button>

            <el-button
                type="text"
                size="small" @click="updateComment(comment.cid, index)">
              修改
            </el-button>
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
import Header from "../../components/Header";
import {getToken} from "../../util/cookie-util";
import {
  getAllPlates, getPost,
  queryCommentsByUId, removeCommentsByCId,
  updateComment,
  updateUserAvailable,
} from "../../request"

import {formatDate} from "../../util/date-util";

export default {
  name: "ManageComment",
  components: {Header},
  data() {
    return {
      list: [
        {cid: 0, postId: 2, publishDatetime: 0, text: '', replyComments: []}
      ],
      pagination: {
        pageNum: 1,
        pageSize: 5,
        total: 0
      },
    }
  },
  mounted() {
    this.getData(this.pagination.pageNum, this.pagination.pageSize)
    getAllPlates().then(data => {
      this.posts = data.data
    })
  },
  methods: {
    disSeal(index, available, uid) {
      console.log(!available, uid)
      this.$confirm('是否更改?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '操作成功!'
        })
        this.list[index].available = !available
        updateUserAvailable(getToken(), !available, uid).then(data => {
          console.log(data)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    getData(pageNum, pageSize) {
      queryCommentsByUId(getToken(), pageNum, pageSize).then(data => {
        console.log(data)
        this.list = data.data.list
        this.pagination.total = data.data.total
      })
    },
    handleCurrentChange(i) {
      this.getData(i, this.pagination.pageSize)
    },
    getPost(postId) {
      getPost(postId).then(data => {
        if (data && data.code === 600) {
          this.$alert('帖子：' + data.data.post.postName, '发表在', {
            confirmButtonText: '确定',
          })
        } else {
          this.$message.error(data.msg)
        }
      })
    },
    updateComment(cid, index) {
      this.$prompt('请填入评论内容', '修改评论', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        if (value && value !== '') {
          updateComment(getToken(), cid, value).then(data => {
            if (data && data.code === 600) {
              this.$message({
                type: 'success',
                message: '修改成功'
              })
              this.list[index].text = value
            } else {
              this.$message({
                type: 'error',
                message: data.msg
              })
            }
          })
        } else {
          this.$message({
            type: 'error',
            message: '评论内容不为空'
          })
        }
      })
    },
    removeComment(cid, index) {
      this.$confirm('将永久删除该评论, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        removeCommentsByCId(getToken(), cid).then(data => {
          if (data.code === 600) {
            this.$message({
              showClose: true,
              message: '删除成功',
              type: 'success'
            })
            if (this.list && this.list.length > index) {
              this.list.splice(index, 1)
            }
          } else {
            this.$message({
              showClose: true,
              message: data.msg,
              type: 'error'
            })
          }
        })
      })
    },
    formatDate(timestamp) {
      return formatDate(timestamp)
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
  border-top: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
  padding-left: 10px;
  padding-right: 10px;
}

.post-item {
  border-bottom: 1px solid #ccc;
  padding: 15px 10px 10px;
  line-height: normal;
}

.post-item:hover {
  background: #ecf0f1;
}

.pagination {
  margin-top: 20px;
}

.comment-content {
  cursor:pointer;
}

.comment-content:hover {
  color: #409EFF;
}

.grade-col {
  cursor: pointer;
}

.grade-col:hover {
  color: #3498db;
}
</style>
