<template>
  <div class="item" @click="handleClick">
    <div class="left">

    </div>
    <div class="right">
      <div class="title">{{title}}</div>
      <div class="post-count">{{postCount}}</div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'PlateItem',
    props: {
      title: {
        type: String,
        default: ''
      },
      postCount: {
        type: Number,
        default: 0
      },
      to: {
        type: String,
        default: ''
      }
    },
    methods: {
      handleClick() {
        this.$router.push(this.to)
      }
    }
  }
</script>

<style scoped>
  .item {
    width: 250px;
    height: 70px;
    border-radius: 7px;
    overflow: hidden;
    float: left;
  }

  .item:hover {
    color: #fffdf1;
    cursor: pointer;
  }

  .left {
    float: left;
    width: 70px;
    height: inherit;
    background: url("../assets/board.png") #ecf0f1;
    background-size: cover;
  }

  .right {
    float: right;
    width: 160px;
    height: 60px;
    background: #ced6e0;
    padding: 5px 10px;
  }

  .title {
    font-weight: bold;
    font-size: large;

  }

  .post-count {
    color: #434343;
    margin-top: 17px;
  }
</style>
