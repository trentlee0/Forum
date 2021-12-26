<template>
  <div class="header">
    <el-header>
      <div class="navigate" @click="handleCommand('/home')">
        论坛
      </div>

      <div class="title">{{ title }}</div>

      <div class="search">
        <el-input v-model="searchInput" placeholder="请输入帖名" @focus="searchResultDisplay = true"
                  @blur="searchResultDisplay = false" @input="search" id="searchInput">
          <i slot="suffix" class="el-input__icon el-icon-search"></i>
        </el-input>
        <ul class="search-result" v-if="searchResultDisplay">
          <li class="search-result-item" v-for="post in posts" :key="post.postId" @mousedown="toPostPage(post.postId)">
            <div class="search-result-item-postname">{{ post.postName }}</div>
            <div class="search-result-item-username">{{ post.user.uname }}</div>
          </li>
        </ul>
      </div>

      <div class="user-icon">
        <el-dropdown @command="handleCommand">
          <img class="avatar" v-if="avatar === null || avatar === ''" src="@/assets/avatar.png" alt="">
          <img class="avatar" v-else :src="avatar" alt="">
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="" disabled>{{ uname }}</el-dropdown-item>
            <el-dropdown-item command="/seal" v-show="userSeal">用户账号管理</el-dropdown-item>
            <el-dropdown-item command="/manageplate" v-show="userPost">我的板块</el-dropdown-item>
            <el-dropdown-item command="/addpost" v-show="!isLogout">发帖子</el-dropdown-item>
            <el-dropdown-item command="/managecomment" v-show="!isLogout">我的评论</el-dropdown-item>
            <el-dropdown-item command="/managepost" v-show="!isLogout">我的帖子</el-dropdown-item>
            <el-dropdown-item command="/my" v-show="!isLogout">个人信息</el-dropdown-item>
            <el-dropdown-item command="/login" v-show="isLogout">登录</el-dropdown-item>
            <el-dropdown-item command="logout" v-show="!isLogout">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
  </div>
</template>

<script>
import {logout, search} from "../request";
import {getCookie, removeCookie} from "../util/cookie-util";

export default {
  name: 'Header',
  components: {},
  props: {
    title: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      searchInput: '',
      size: 40,
      posts: [
        // {postId: 0, postName: ''}
      ],
      searchResultDisplay: false
    }
  },
  methods: {
    handleCommand(command) {
      if (command === 'logout') {
        logout(getCookie('token')).then(data => {
          if (data.data) {
            removeCookie('token')
            this.$router.replace('/home');
            this.$router.go(0);
          }
        })
      } else {
        let path = command
        if (this.$route.path !== path)
          this.$router.push(path)
      }
    },
    search() {
      if (this.searchInput && this.searchInput !== '') {
        search(this.searchInput).then(data => {
          console.log(data)
          this.posts = data.data.list
        })
      } else {
        this.posts.splice(0, this.posts.length)
      }
    },
    toPostPage(postId) {
      this.$router.push('/post/' + postId)
      this.$router.go(0)
      console.log('click')
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    },
    uname() {
      return this.$store.state.user ? this.$store.state.user.uname : ''
    },
    avatar() {
      return this.$store.state.user ? this.$store.state.user.avatar : ''
    },
    userSeal() {
      return this.user && this.user.ugrade === 3
    },
    userPost() {
      return this.user && this.user.ugrade >= 2
    },
    isLogout() {
      return !this.user || this.user.uid === 0
    }
  }
}
</script>

<style scoped>
.header {
  background: #ecf0f1;
}

.el-header {
  border-bottom: 1px solid #DDDFE5;
  position: relative;
}

.title {
  position: absolute;
  width: 120px;
  text-align: center;
  /*background: #3498db;*/
  top: 25px;
  font-size: larger;
  left: calc(50% - 60px);
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50px;
}

.navigate {
  position: absolute;
  left: 30px;
  top: 15px;
  font-weight: bold;
  font-size: x-large;
}

.navigate:hover {
  cursor: pointer;
}

.search {
  width: 200px;
  height: 30px;
  position: absolute;
  right: 200px;
  top: 8px;
}

.search-result {
  padding: 0;
  list-style: none;
  background: #f5f5f5;
  margin: 5px;
  padding: 0 5px;
  position: relative;
  z-index: 10;
}

.search-result-item {
  margin: 0;
  padding: 0;
  list-style-type: none;
  border-bottom: 1px solid #ccc;
  padding: 12px;
}

.search-result-item:hover {
  background: #97b2b0;
  cursor: pointer;
}

.search-result-item-postname {

}

.search-result-item-username {
  font-size: 12px;
  color: #737373;
}

.user-icon {
  position: absolute;
  right: 30px;
  top: 5px;
}
</style>
