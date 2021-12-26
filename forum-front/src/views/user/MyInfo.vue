<template>
  <div>
    <div>
      <Header/>
    </div>
    <div class="message">
      <el-descriptions class="margin-top" title="个人信息" :column="2" border>
        <template slot="extra">
          <el-button type="primary" size="small" @click="openEdit">编辑</el-button>
          <div>
            <el-dialog title="个人信息" :visible.sync="dialogFormVisible">
              <el-form :model="userForm">
                <el-form-item label="用户名" :label-width="formLabelWidth">
                  <el-input v-model="userForm.username" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="性别" :label-width="formLabelWidth">
                  <el-select v-model="userForm.gender" placeholder="请选择性别">
                    <el-option label="男" :value="0"></el-option>
                    <el-option label="女" :value="1"></el-option>
                  </el-select>
                </el-form-item>

                <el-form-item label="密码" :label-width="formLabelWidth">
                  <el-input v-model="userForm.password" show-password autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="出生日期" :label-width="formLabelWidth">
                  <el-date-picker
                      v-model="userForm.birthday"
                      align="right"
                      type="date"
                      placeholder="选择日期"
                      :picker-options="pickerOptions">
                  </el-date-picker>
                </el-form-item>

                <el-form-item label="头像" :label-width="formLabelWidth">
                  <el-upload
                      class="avatar-uploader"
                      :action="uploadURL"
                      :multiple="false"
                      :show-file-list="false"
                      :on-success="handleAvatarSuccess"
                      :before-upload="beforeAvatarUpload">
                    <img v-if="imageUrl" :src="imageUrl" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="cancelDialog" style="margin-right: 20px;">取 消</el-button>
                <el-popconfirm title="确定要修改吗？" @confirm="confirmDialog">
                  <el-button type="primary" slot="reference">确 定</el-button>
                </el-popconfirm>
              </div>
            </el-dialog>
          </div>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            用户名
          </template>
          {{ user.uname }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-time"></i>
            生日
          </template>
          {{ formatBirthday(user.ubirthday) }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-custom"></i>
            性别
          </template>
          {{ user.ugender === 0 ? '男' : '女' }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            等级
          </template>
          <el-tag size="small">{{ getUserGrade(user.ugrade) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user-solid"></i>
            头像
          </template>
          <div class="block">
            <img class="avatar" v-if="user.avatar === null || user.avatar === ''" src="@/assets/avatar.png" alt="">
            <img class="avatar" v-else :src="user.avatar" alt="">
          </div>
        </el-descriptions-item>
      </el-descriptions>
    </div>
  </div>
</template>

<script>
import Header from '../../components/Header'
import {formatBirthday} from "../../util/date-util"
import {getUploadAvatarURL, updateUser} from "../../request"
import {getToken} from "../../util/cookie-util"
import {getUserGrade} from "../../util/user-grade"


export default {
  name: 'MyInfo',
  components: {Header},
  data() {
    return {
      imageUrl: '',
      dialogTableVisible: false,
      dialogFormVisible: false,
      userForm: {
        username: '',
        password: '',
        gender: 0,
        birthday: 0,
        avatar: '',
      },
      formLabelWidth: '120px',
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }],
      },
    }
  },
  computed: {
    user() {
      return this.$store.state.user
    },
    uid() {
      return this.$store.state.user.uid
    },
    uploadURL() {
      return getUploadAvatarURL()
    }
  },
  methods: {
    getUserGrade(ugrade) {
      return getUserGrade(ugrade)
    },
    formatBirthday(timestamp) {
      return formatBirthday(timestamp)
    },
    cancelDialog() {
      this.dialogFormVisible = false
    },
    confirmDialog: function () {
      this.dialogFormVisible = false
      let birthday = new Date(this.userForm.birthday).getTime().toString()
      updateUser(getToken(),
          this.userForm.username,
          this.userForm.password,
          this.userForm.gender,
          birthday,
          this.userForm.avatar
      ).then(data => {
        console.log(data)
        if (data.code === 600) {
          this.$message({
            showClose: true,
            message: "修改成功",
            type: 'success'
          })

          let user = this.userForm
          this.$store.commit('updateUname', user.username)
          this.$store.commit('updateUbitthday', user.birthday)
          this.$store.commit('updateUgender', user.gender)
          this.$store.commit('updateAvatar', user.avatar)
          this.userForm.password = ''
        } else {
          this.$message({
            showClose: true,
            message: data.msg,
            type: 'error'
          })
        }
      })
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.userForm.avatar = res.data
    },
    beforeAvatarUpload(file) {
      const size = 5;
      const isJPGOrPNG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLimit = file.size / 1024 / 1024 < size;

      if (!isJPGOrPNG) {
        this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
      }
      if (!isLimit) {
        this.$message.error('上传头像图片大小不能超过 ' + size + 'MB!');
      }
      return isJPGOrPNG && isLimit;
    },
    openEdit() {
      this.userForm.username = this.user.uname
      this.userForm.gender = this.user.ugender
      this.userForm.birthday = this.user.ubirthday
      this.userForm.avatar = this.user.avatar

      this.dialogFormVisible = true
    }
  }
}
</script>

<style scoped>
.message {
  width: 810px;
  margin: 100px auto 0;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 128px;
  height: 128px;
  line-height: 178px;
  text-align: center;
}

.info-avatar {
  width: 100px;
  height: 100px;
  border-radius: 100px;
}

.avatar {
  width: 128px;
  height: 128px;
  display: block;
}
</style>
