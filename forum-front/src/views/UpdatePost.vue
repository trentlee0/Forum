<template>
  <div>
    <div>
      <Header/>
    </div>

    <div class="container">
      <div class="post-title clearfix">
        <div class="fl input-title">
          <el-input v-model="post.postName" placeholder="请输入文章标题" maxlength="30" show-word-limit></el-input>
        </div>
        <el-button @click="savePost">保存</el-button>
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
import {getPost, getUploadImageURL, getUploadVideoURL, updateUserPost} from "../request";
import {getCookie} from "../util/cookie-util";

export default {
  name: 'UpdatePost',
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
    getPost(this.postId).then(data => {
      this.post = data.data.post
      this.editor.txt.html(this.post.content)
    })
  },
  data() {
    return {
      editor: null,
      post: {
        postId: 0,
        postName: '',
        content: '',
      }
    }
  },
  methods: {
    savePost() {
      this.post.content = this.editor.txt.html()
      updateUserPost(getCookie('token'),
          this.$store.state.user.uid,
          this.post.postId,
          this.post.content,
          this.post.postName
      ).then(data => {
        if (data.code === 600 && data.data === 1) {
          this.$message({
            showClose: true,
            message: '修改成功',
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
  },
  computed: {
    postId() {
      return this.$route.params.id
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
