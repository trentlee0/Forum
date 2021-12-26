<template>
  <div>
    <h1 class="center" style="cursor: pointer;" @click="$router.push('/home')">论坛</h1>
    <el-card class="box-card">
      <h2 class="center">用户注册</h2>
      <el-form :model="registerForm" status-icon ref="registerForm" label-width="100px">
        <div class="item">
          <el-input v-model="registerForm.username" placeholder="用户名" :maxlength="10"/>
        </div>
        <div class="item">
          <el-input show-password v-model="registerForm.password" placeholder="密码" :maxlength="16"/>
        </div>
        <div class="item">
          <el-input show-password v-model="registerForm.checkpass" placeholder="确认密码" :maxlength="16"/>
        </div>
        <div class="btn">
          <el-button class="login-btn" type="primary" @click="handerRegister">注册</el-button>
        </div>
        <div class="btn">
          <el-button class="login-btn" @click="toLogin">去登录</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
  import {register} from '../request'

  export default {
    name: 'Register',
    data() {
      return {
        registerForm: {
          username: '',
          password: '',
          checkpass: ''
        }
      }
    },
    methods: {
      toLogin() {
        this.$router.push('/login')
      },
      handerRegister() {
        if (this.registerForm.username == '') {
          this.$message({
            showClose: true,
            message: '用户名不能为空！',
            type: 'error'
          })
          return
        } else if (this.registerForm.password == '') {
          this.$message({
            showClose: true,
            message: '密码不能为空！',
            type: 'error'
          })
          return
        } else if (this.registerForm.checkpass == '') {
          this.$message({
            showClose: true,
            message: '确认密码不能为空！',
            type: 'error'
          })
          return
        } else if (this.registerForm.password != this.registerForm.checkpass) {
          this.$message({
            showClose: true,
            message: '两次输入的密码不相同！',
            type: 'error'
          })
          return
        }

        register(this.registerForm.username, this.registerForm.password).then(data => {
          let user = data.data
          if (user === 0) {
            this.$message({
              showClose: true,
              message: data.msg,
              type: 'error'
            })
          } else {
            this.$message({
              showClose: true,
              message: '注册成功',
              type: 'success'
            })
          }
          this.$router.push('/login')
        })
      }
    }
  }
</script>

<style scoped>
  .box-card {
    width: 480px;
    margin: 60px auto;
    padding-bottom: 50px;
  }

  .center {
    text-align: center;
  }

  .item {
    margin: 20px auto;
    width: 350px;
  }

  .login-btn {
    margin: 0 auto;
    width: 350px;
  }

  .btn {
    margin: 10px auto;
    width: 350px;
  }
</style>
