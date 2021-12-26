<template>
  <div>
    <h1 class="center" style="cursor: pointer;" @click="$router.push('/home')">论坛</h1>
    <el-card class="box-card">
      <h2 class="center">用户登录</h2>
      <el-form :model="loginForm" status-icon ref="loginForm" label-width="100px">
        <div class="item">
          <el-input v-model="loginForm.username" placeholder="用户名"/>
        </div>
        <div class="item">
          <el-input show-password v-model="loginForm.password" placeholder="密码"/>
        </div>
        <div class="btn">
          <el-button class="login-btn" type="primary" @click="handleLogin">登录</el-button>
        </div>
        <div class="btn">
          <el-button class="login-btn" @click="toRegister">注册</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
  import {login} from '../request'
  import {setCookie} from "../util/cookie-util"

  export default {
    name: 'Login',
    props: {},
    mounted() {
      document.addEventListener('keydown', (e) => {
        if (this.$route.path === '/login' && e.key === 'Enter') this.handleLogin()
      })
    },
    data() {
      return {
        loginForm: {
          username: '',
          password: ''
        }
      }
    },
    methods: {
      handleLogin() {
        if (this.loginForm.username === ''){
          this.$message({
            showClose: true,
            message: '用户名不能为空！',
            type: 'error'
          })
          return
        } else if (this.loginForm.password === '') {
          this.$message({
            showClose: true,
            message: '密码不能为空！',
            type: 'error'
          })
          return
        }

        login(this.loginForm.username, this.loginForm.password).then(data => {
          let user = data.data
          if (user == null) {
            this.$message({
              showClose: true,
              message: data.msg,
              type: 'error'
            })
          } else {
            this.$message({
              showClose: true,
              message: '登录成功',
              type: 'success'
            })

            this.$store.commit('updateUser', data.data.user)
            setCookie("token", data.data.token)
            this.$router.push('/home')
          }
        })
      },
      toRegister() {
        this.$router.push('/register')
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
