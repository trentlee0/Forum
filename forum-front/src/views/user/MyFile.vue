<template>
  <div>
    <el-upload
        class="avatar-uploader"
        :action="uploadURL"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload">
      <img v-if="imageUrl" :src="imageUrl" class="avatar" alt="">
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>
  </div>
</template>

<script>
import {getUploadURL} from "../../request";

export default {
  name: "MyFile",
  data() {
    return {
      imageUrl: ''
    };
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      console.log(res)
      console.log(file)
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
    }
  },
  computed: {
    uploadURL() {
      console.log(getUploadURL())
      return getUploadURL();
    }
  }
}
</script>

<style scoped>
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
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
