<template>
  <div>
    <Header/>
    <div class="container">
      <div class="title clearfix">
      </div>

      <div class="text-title clearfix">
        我的板块
        <div class="fr" v-if="isShowAddPlateBtn">
          <el-button type="primary" size="small" plain @click="addPlate">添加板块</el-button>
        </div>
      </div>
      <div class="post-items">
        <el-row class="post-item" :gutter="20">
          <el-col :span="8">
            <div class="grid-content bg-purple">版主</div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple">板块名</div>
          </el-col>
          <el-col :span="4">
            <div class="grid-content bg-purple">操作</div>
          </el-col>
        </el-row>

        <el-row class="post-item" :gutter="20" v-for="(plate, index) in plates" :key="plate.pid">
          <el-col :span="8">
            <div>{{ plate.pid }}</div>
          </el-col>
          <el-col :span="8">
            <span class="post-name" @click="toPostPage(plate.pid)">{{ plate.pname }}</span>
          </el-col>
          <el-col :span="4">
            <el-button @click="openEditor(plate.pid, plate.pname, index)" size="small" type="text">修改</el-button>
            <el-button @click="removePlate(plate.pid, index)" size="small" type="text">删除</el-button>
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
import {addPlate, getPlateUserByToken, removePlate, updateByUId} from "../../request";
import {getToken} from "../../util/cookie-util";
import {formatDate} from "../../util/date-util";
import {ADMIN} from '../../util/user-grade'

export default {
  name: "ManagePlate",
  components: {Header},
  data() {
    return {
      plates: [
        {
          uid: 0,
          pid: 0,
          pname: ''
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
  computed: {
    user() {
      return this.$store.state.user
    },
    isShowAddPlateBtn() {
      return this.$store.state.user.ugrade === ADMIN
    }
  },
  methods: {
    getData(pageNum, pageSize) {
      getPlateUserByToken(getToken(), pageNum, pageSize).then(data => {
        this.plates = data.data.list
        this.pagination.total = data.data.total
      })
    },
    handleCurrentChange(i) {
      this.getData(i, this.pagination.pageSize)
    },
    toPostPage(postId) {
      this.$router.push('/manageplatepost/' + postId)
    },
    formatDate(timestamp) {
      return formatDate(timestamp)
    },
    openEditor(plateId, plateName, index) {
      console.log(plateId)
      this.$prompt('请输入板块名', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: plateName
      }).then(({value}) => {
        if (!value) {
          this.$message({
            showClose: true,
            message: '请输入板块名',
            type: 'error'
          })
          return
        }

        updateByUId(getToken(), plateId, value).then(data => {
          console.log(data)
          this.$message({
            type: 'success',
            message: '修改成功，为' + value
          })
          this.plates[index].pname = value
        })
      })
    },
    addPlate() {
      this.$prompt('请输入板块名', '添加板块', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        if (value && value !== '') {
          addPlate(getToken(), value).then(data => {
            if (data && data.code === 600) {
              this.$router.go(0)
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
            message: '板块名不为空'
          })
        }
      })
    },
    removePlate(plateId, index) {
      this.$confirm('将永久删除该板块, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        removePlate(getToken(), plateId).then(data => {
          if (data && data.code === 600) {
            this.$message({
              type: 'success',
              message: '删除成功'
            })

            this.plates.splice(index, 1)
          } else {
            this.$message({
              type: 'error',
              message: data.msg
            })
          }
        })
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
}

.post-item:first-of-type {
  border-top: 1px solid #ccc;
}

.post-item {
  padding: 13px 0;
  border-bottom: 1px solid #ccc;
}

.post-item:hover {
  background: #cccccc;
}

.post-name:hover {
  cursor: pointer;
  color: #3498db;
}

.pagination {
  margin-top: 20px;;
}
</style>
