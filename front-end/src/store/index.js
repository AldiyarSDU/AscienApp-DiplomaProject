import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    showModal: false,
    showVideo: false,
    overlay: false,
    user: null,
    answers: [],
  },
  mutations: {
    SET_SHOW_VIDEO (state) {
      state.showVideo = !state.showVideo
    },
    IS_OVERLAY (state) {
      state.overlay = true
    },
    IS_NOT_OVERLAY (state) {
      state.overlay = false
    },
    SET_ANSWERS (state, [i, answer]) {
      state.answers[i] = answer
    },
    SET_USER (state, user) {
      state.user = user
    }
   },
  actions: {
    SHOW_VIDEO_ACTION ({commit}) {
      commit('SET_SHOW_VIDEO')
    },
    OVERLAY_ACTION ({commit}) {
      commit('IS_OVERLAY')
    },
    NOT_OVERLAY_ACTION ({commit}) {
      commit('IS_NOT_OVERLAY')
    },
    USER_ACTION ({commit}, user) {
      commit('SET_USER', user)
    }
  },
  getters: {
    GET_SHOW_VIDEO (state) {
      return state.showVideo
    },
    GET_OVERLAY (state) {
      return state.overlay
    },
    GET_ANSWERS (state) {
      return state.answers
    },
    GET_USER (state) {
      return state.user
    }
  },
  modules: {
  }
})
