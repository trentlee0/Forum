import VueRouter from 'vue-router'
import Vue from 'vue'
import Register from '../views/Register'
import Login from '../views/Login'
import Home from '../views/Home'
import Plate from '../views/Plate'
import Post from '../views/Post'
import AddPost from '../views/AddPost'
import MyInfo from '../views/user/MyInfo'
import Seal from '../views/user/Seal'
import ManagePost from '../views/user/ManagePost'
import MyFile from "../views/user/MyFile"
import UpdatePost from "../views/UpdatePost"
import ManagePlate from "../views/user/ManagePlate"
import ManagePlatePost from "../views/user/ManagePlatePost"
import NotFound from "../views/NotFound"
import ManageComment from "../views/user/ManageComment";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/home',
  },
  {
    path: '/home', component: Home,
    meta: {
      title: '论坛 - 首页'
    }
  },
  {
    path: '/login', component: Login,
    meta: {
      title: '论坛 - 登录'
    }
  },
  {
    path: '/register', component: Register,
    meta: {
      title: '论坛 - 注册'
    }
  },
  {
    path: '/plate/:id', component: Plate,
    meta: {
      title: '论坛 - 板块帖子'
    }
  },
  {
    path: '/post/:id', component: Post,
    meta: {
      title: '论坛 - 帖子详情'
    }
  },
  {
    path: '/addpost', component: AddPost,
    meta: {
      title: '论坛 - 发帖子'
    }
  },
  {
    path: '/my', component: MyInfo,
    meta: {
      title: '论坛 - 我的信息'
    }
  },
  {
    path: '/seal', component: Seal,
    meta: {
      title: '论坛 - 用户账号管理'
    }
  },
  {
    path: '/managepost', component: ManagePost,
    meta: {
      title: '论坛 - 我的帖子'
    }
  },
  {
    path: '/myfile', component: MyFile,
    meta: {
      title: '论坛 - 我的文件'
    }
  },
  {
    path: '/update-post/:id', component: UpdatePost,
    meta: {
      title: '论坛 - 修改帖子'
    }
  },
  {
    path: '/managecomment', component: ManageComment,
    meta: {
      title: '论坛 - 管理评论'
    }
  },
  {
    path: '/manageplate', component: ManagePlate,
    meta: {
      title: '论坛 - 我的板块'
    }
  },
  {
    path: '/manageplatepost/:plateid', component: ManagePlatePost,
    meta: {
      title: '论坛 - 管理板块帖子'
    }
  },
  {
    path: "/404",
    component: NotFound,
    meta: {
      title: '论坛 - 404'
    }
  },
  {
    path: "*",
    redirect: "/404"
  },

]

export default new VueRouter({
  mode: 'history',
  routes
})
