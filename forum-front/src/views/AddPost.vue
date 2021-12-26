<template>
  <div>
    <div>
      <Header/>
    </div>

    <div class="container">
      <div class="post-title clearfix">
        <div class="fl input-title">
          <el-input v-model="postName" placeholder="请输入文章标题" maxlength="30" show-word-limit></el-input>
        </div>
        <div class="fl">
          <el-button type="primary" @click="open">发布</el-button>
          <el-dialog
              title="选择板块"
              :visible.sync="dialogVisible"
              width="30%"
              center>
            <el-dropdown @command="handleCommand">
              <span class="el-dropdown-link">
                板块名<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item v-for="post in posts" :command="post.pid" :key="post.pid">{{ post.pname }}
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>

          </el-dialog>
        </div>
      </div>
      <hr>
      <div class="post-text">
        <div id="editor"></div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from '../components/Header'
import E from 'wangeditor'
import {addPost, getAllPlates, getUploadImageURL, getUploadVideoURL} from "../request";
import {getCookie} from "../util/cookie-util";

export default {
  name: 'AddPost',
  components: {Header},
  mounted() {
    this.editor = new E('#editor')
    this.editor.config.height = 400
    this.editor.config.zIndex = 1000
    this.editor.config.uploadImgMaxLength = 1
    this.editor.config.uploadFileName = 'file'
    this.editor.config.uploadVideoName = 'file'
    // this.editor.config.uploadVideoHooks = {
    //   customInsert: function (insertVideoFn, result) {
    //     console.log(result)
    //   }
    // }
    // this.editor.config.uploadImgHooks = {
    //   customInsert: function (insertImgFn, result) {
    //     console.log(result)
    //   }
    // };

    this.editor.config.uploadImgServer = getUploadImageURL()
    this.editor.config.uploadVideoServer = getUploadVideoURL()
    this.editor.create()
    getAllPlates().then(data => {
      this.posts = data.data
    })
  },
  data() {
    return {
      postName: '',
      dialogVisible: false,
      editor: null,
      posts: [
        {count: 0, pname: '', pid: 0}
      ]
    }
  },
  methods: {
    open() {
      if (this.postName === '') {
        this.$message({
          showClose: true,
          message: '请输入文章标题',
          type: 'error'
        })
        return
      }

      this.dialogVisible = true
      this.editor.config.zIndex = 1
    },
    handleCommand(plateId) {
      this.dialogVisible = false
      this.editor.config.zIndex = 1000

      addPost(
          getCookie('token'),
          this.$store.state.user.uid,
          plateId,
          this.postName,
          this.editor.txt.html()
      ).then(data => {
        if (data.data === 1) {
          this.$message({
            showClose: true,
            message: '发布成功',
            type: 'success'
          })
          this.$router.back()
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
  border-radius: 15px;
  width: 850px;
  margin: 0 auto;
  margin-top: 5px;
  padding: 50px;
  background: #ecf0f1;
}

.post-title {
  margin-bottom: 30px;
}

.submit-btn {
  margin: 0 auto;
}

.post-text {

}

.input-title {
  width: 710px;
  margin-right: 20px;
}

.toolbar {
  border: 1px solid #ccc;
}

.footer {
  /*margin-bottom: 100px;*/
  height: 100px;
}

.text {
  border: 1px solid #ccc;
  min-height: 400px;
}

</style>
