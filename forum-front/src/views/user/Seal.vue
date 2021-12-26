<template>
  <div>
    <div>
      <Header/>
    </div>
    <div class="container clearfix">
      <div class="text-title">
        用户账号管理
      </div>
      <div class="clearfix"></div>
      <div class="post-items">
        <el-row class="post-item" :gutter="20">
          <el-col :span="8">
            <div class="grid-content bg-purple">用户ID</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">姓名</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">用户等级</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">操作</div>
          </el-col>
        </el-row>

        <el-row class="post-item" :gutter="20" v-for="(user, index) in list" :key="index">
          <el-col :span="8">
            <span class="post-name">{{ user.uid }}</span>
          </el-col>
          <el-col :span="4">
            <div class="post-user">{{ user.uname }}</div>
          </el-col>
          <el-col :span="4">
            <div class="post-user grade-col" @click="showPlateGrade(index)">{{ getUserGrade(user.ugrade) }}</div>
          </el-col>
          <el-col :span="4">
            <el-button
                type="text"
                size="small" @click="disSeal(index, user.available, user.uid)">
              {{ user.available === true ? '封禁' : '解封' }}
            </el-button>

            <el-button
                type="text"
                size="small" @click="dealGrade(index, user.uid)">
              修改权限
            </el-button>
          </el-col>
        </el-row>

        <el-dialog
            title="该版主管理的板块"
            :visible.sync="gradeDialogVisible"
            width="30%">
          <el-card class="box-card">
            <div v-for="(plate,i) in userPlates" :key="plate.pid" class="text item clearfix">
              <div class="fl" style="margin-top: 5px;">{{ plate.pname }}</div>
              <el-button size="small" class="fr" style="margin-top: 0" type="text"
                         @click="removeUserPlate(plate.pid, i)">
                移除
              </el-button>
              <hr size="1px" color="#f5f5f5"/>
            </div>
          </el-card>
          <span slot="footer" class="dialog-footer">
              <el-button @click="gradeDialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="gradeDialogVisible = false">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog
            title="选择权限"
            :visible.sync="dialogVisible"
            width="30%"
            center>
          <el-tabs type="card" v-model="gradeTab">
            <el-tab-pane label="普通用户" name="1">
              <el-button
                  style="margin-left: 10px;"
                  type="text"
                  size="middle" @click="dealNormalGrade">
                确认
              </el-button>
            </el-tab-pane>
            <el-tab-pane label="版主" name="2">
              <el-dropdown @command="dealPlateGrade">
                    <span class="el-dropdown-link">
                      请选择板块<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item v-for="post in posts" :command="post.pid" :key="post.pid">{{ post.pname }}
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </el-tab-pane>
          </el-tabs>
        </el-dialog>

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
import {
  getAllPlates,
  getAllUser,
  getPlateUserByUId,
  removeUserPlate,
  updateUserAvailable,
  updateUserGrade
} from "../../request"
import {getToken} from "../../util/cookie-util"
import {getUserGrade, MODERATOR} from "../../util/user-grade"

export default {
  name: 'Seal',
  components: {Header},
  data() {
    return {
      list: [
        {available: true, ugrade: 2, ugender: 0, uname: '', uid: 0}
      ],
      pagination: {
        pageNum: 1,
        pageSize: 5,
        total: 0
      },
      dialogVisible: false,
      posts: [
        {count: 0, pname: '', pid: 0}
      ],
      gradeTab: '1',
      activeUId: 0,
      activeIndex: 0,
      gradeDialogVisible: false,
      userPlates: [{pid: 0, pname: '', uid: 0}]
    }
  },
  mounted() {
    this.getData(getToken(), this.pagination.pageNum, this.pagination.pageSize)
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
    getData(token, pageNum, pageSize) {
      getAllUser(token, pageNum, pageSize).then(data => {
        console.log(data)
        this.list = data.data.list
        this.pagination.total = data.data.total
      })
    },
    handleClick(uId) {
      console.log(uId)
    },
    handleCurrentChange(i) {
      this.getData(i, this.pagination.pageSize)
    },
    dealNormalGrade() {
      this.dialogVisible = false
      updateUserGrade(getToken(), this.activeUId, 1).then(data => {
        console.log(data)
        if (data.code === 600) {
          this.$message({
            showClose: true,
            message: '修改成功',
            type: 'success'
          })
          this.$router.go(0)
        } else {
          this.$message({
            showClose: true,
            message: data.msg,
            type: 'error'
          })
        }
      })
    },
    dealPlateGrade(plateId) {
      console.log(plateId)
      this.dialogVisible = false
      updateUserGrade(getToken(), this.activeUId, 2, plateId).then(data => {
        console.log(data)
        if (data.code === 600) {
          this.$message({
            showClose: true,
            message: '修改成功',
            type: 'success'
          })
          this.$router.go(0)
        } else {
          this.$message({
            showClose: true,
            message: data.msg,
            type: 'error'
          })
        }
      })
    },
    dealGrade(index, uId) {
      this.dialogVisible = true
      this.activeUId = uId
      this.activeIndex = index
    },
    getUserGrade(ugrade) {
      return getUserGrade(ugrade)
    },
    showPlateGrade(index) {
      if (this.list[index].ugrade === MODERATOR) {
        this.gradeDialogVisible = true
        getPlateUserByUId(this.list[index].uid).then(data => {
          if (data.code === 600) {
            this.userPlates = data.data.list
          }
        })
      } else {
        this.$message({
          showClose: true,
          message: '这是普通用户哦',
          type: 'info'
        })
      }
    },
    removeUserPlate(plateId, index) {
      removeUserPlate(getToken(), plateId).then(data => {
        if (data.code === 600) {
          this.$message({
            showClose: true,
            message: '移除成功',
            type: 'success'
          })
          if (this.userPlates && this.userPlates.length > index) {
            this.userPlates.splice(index, 1)
          }
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

.grade-col {
  cursor: pointer;
}

.grade-col:hover {
  color: #3498db;
}
</style>
