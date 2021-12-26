<template>
  <div id="app">
    <RouterView/>
  </div>
</template>

<script>

import {getToken} from "./util/cookie-util";
  import {getUser} from "./request";

  export default {
    name: 'App',
    created() {
      let token = getToken();
      if (token) {
        console.log(token)
        getUser(token).then(data => {
          if (data.code === 600)
            this.$store.commit("updateUser", data.data)
        })
      }
    },
  }
</script>

<style>
  @import "assets/style/common.css";
  @import "~normalize.css";
</style>
