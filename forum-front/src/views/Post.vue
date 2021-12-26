<template>
  <div>
    <div>
      <Header/>
    </div>

    <div class="container clearfix">
      <div class="btn-back">
        <el-breadcrumb style="margin-top: 10px;" separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{path: '/plate/' + post.plate.pid}">{{ post.plate.pname }}</el-breadcrumb-item>
          <el-breadcrumb-item :to="{path: '/post/' + post.postId}">{{ post.postName }}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="post clearfix">
        <div class="post-user fl">
          <el-avatar :size="100" :src="post.user.avatar"></el-avatar>
          <div align="center" style="padding: 10px;">
            {{ post.user.uname }}
          </div>
        </div>
        <div class="post-card fl">
          <div class="post-title">
            <span class="datetime">发表于：{{ formatDate(post.updateDatetime) }}</span><br><br>
            <span class="title">{{ post.postName }}</span>
          </div>
          <hr color="#DCDFE5" size="1px"/>
          <div class="post-text" v-html="post.content">
          </div>
          <div class="approve">
            <el-badge :value="approvePostCount" class="item">
              <el-button size="small" @click="handleApprove">点赞</el-button>
            </el-badge>
          </div>
        </div>
      </div>

      <div v-for="comment in comments" :key="comment.user.uid">
        <hr/>
        <div class="reply">
          <div class="comment-item clearfix">
            <div class="reply-user" align="center">
              <el-avatar :size="100" :src="comment.user.avatar"></el-avatar>
              <div>{{ comment.user.uname }}</div>
            </div>
            <div class="reply-container">
              <div class="reply-text">
                <div class="write-back">
                  <span class="datetime">发表于：{{
                      formatDate(comment.publishDatetime)
                    }}</span><br><br>
                  {{ comment.text }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="send-comment">
        <el-card class="box-card">
          <div class="comment-title">发表评论</div>
          <el-input
              type="textarea"
              :rows="2"
              placeholder="请输入内容"
              v-model="commentContent">
          </el-input>
          <div class="send-btn fr">
            <el-button type="primary" size="middle" plain @click="handleReleaseComment">发表</el-button>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import Header from '../components/Header'
import {getPost, addApprove, addComment} from "../request";
import {formatDate} from "../util/date-util"
import {getCookie, getToken} from "../util/cookie-util";

export default {
  name: 'Post',
  components: {Header},
  mounted() {
    getPost(this.postId).then(data => {
      this.comments = data.data.comments
      this.post = data.data.post
      this.approvePostCount = data.data.approvePostCount
    })
  },
  data() {
    return {
      comments: [
        {
          text: '',
          postId: 0,
          publishDatetime: 0,
          user: {
            avatar: null,
            available: true,
            ugender: 0,
            ubirthday: 0,
            ugrade: 0,
            uid: 0,
            uname: ''
          },
          replyComments: [
            {
              rcId: 0,
              text: '',
              publishDatetime: 0,
              user: {
                avatar: null,
                available: true,
                ugender: 0,
                ubirthday: 0,
                ugrade: 0,
                uid: 0,
                uname: ''
              },
            }
          ]
        }
      ],
      post: {
        postId: 0,
        plate: {
          pid: 0,
          pname: '',
          uid: 0
        },
        postName: '',
        createDatetime: 0,
        recycle: false,
        content: '',
        updateDatetime: 0,
        user: {
          avatar: null,
          available: true,
          ugender: 0,
          ubirthday: 0,
          ugrade: 0,
          uid: 0,
          uname: ''
        },
      },
      approvePostCount: 0,
      commentContent: ''
    }
  },
  computed: {
    uid() {
      return this.$store.state.user.uid
    },
    postId() {
      return this.$route.params.id
    }
  },
  methods: {
    toPlate() {
      this.$router.back()
    },
    formatDate(timestamp) {
      return formatDate(timestamp)
    },
    handleApprove() {
      addApprove(this.uid, this.post.postId, getCookie("token")).then(data => {
        console.log(data)
        if (data.data) {
          this.$message({
            showClose: true,
            message: "点赞成功",
            type: 'success'
          })
          this.approvePostCount++
        } else {
          this.$message({
            showClose: true,
            message: data.msg,
            type: 'error'
          })
        }
      })
    },
    handleReleaseComment() {
      addComment(getToken(), this.post.postId, this.uid, this.commentContent).then(data => {
        console.log(data)
        if (data && data.code === 600) {
          this.$message({
            showClose: true,
            message: "发表成功",
            type: 'success'
          })
          this.commentContent = ''
          this.$router.go(0)
        } else {
          this.$message({
            showClose: true,
            message: data.msg,
            type: 'error'
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.container {
  width: 810px;
  margin: 0 auto;
  padding-top: 30px;
  background: #f5f5f5;
  position: relative;
}

.btn-back {
  position: absolute;
  top: 2px;
  left: 2px;
}


.datetime {
  font-size: 10px;
  color: #808080;
}

.post {
  border-top: 1px solid transparent;
  /*height: 600px;*/
  margin: 40px 0;
  /*background: #fffdf1;*/
  position: relative;
  padding: 20px 20px 100px 10px;
}

.post-card {
  /*position: relative;*/
  /*background: #ced6e0;*/
  width: 200px;
}

.post-user {
  float: left;
  margin-top: 20px;
  /*background: #cccccc;*/
}

.comment-item {
  margin: 40px 0;
}

.post-title {
  height: 80px;
  /*background: #698381;*/
}

.title {
  font-size: x-large;
}

.post-text {
  /*position: absolute;*/
  /*left: 150px;*/
  /*background: #3498db;*/
  /*height: 490px;*/
  /*background: #3498db;*/
}

.post-user {
  /*background: yellowgreen;*/
  width: 20%;
  text-align: center;
}

.post-card {
  /*background: cadetblue;*/
  width: 80%;
}

.approve {
  position: absolute;
  right: 10px;
  bottom: 40px;
}

.approve {
  height: 30px;
  /*float: right;*/
  /*background: #434343;*/
}

.reply-user {
  float: left;
  margin-top: 20px;
  padding: 15px;
  width: 150px;
}

.reply-text {
  text-align: center;
}

.write-back {
  text-align: left;
}

.item {
  margin-top: 10px;
  margin-right: 40px;
}

.comment-title {
  margin-bottom: 10px;
}

.send-comment {
  margin-top: 50px;
  padding: 50px 200px;
  border-top: 1px solid #ccc;
  background: #fafbfc;
}

.send-btn {
  margin-top: 10px;
  padding-bottom: 10px;
}
</style>
