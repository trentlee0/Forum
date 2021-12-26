import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      uid: 0,
      uname: '',
      avatar: '',
      available: true,
      ugender: 0,
      ubirthday: 0,
      ugrade: 0
    },
  },
  mutations: {
    updateUser(state, payload) {
      state.user = payload
    },
    updateUname(state, payload) {
      state.user.uname = payload
    },
    updateUgender(state, payload) {
      state.user.ugender = payload
    },
    updateUbitthday(state, payload) {
      console.log(payload)
      state.user.ubirthday = payload
    },
    updateAvatar(state, payload) {
      state.user.avatar = payload
    }
  }
})
