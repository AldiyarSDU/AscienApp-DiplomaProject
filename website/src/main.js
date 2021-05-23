import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import StarRating from 'vue-star-rating'
import axios from "axios";

import { VuePicker, VuePickerOption } from '@invisiburu/vue-picker'
import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/index.css';
import VueTheMask from 'vue-the-mask';
// optional css
import '@invisiburu/vue-picker/dist/vue-picker.min.css'

import VueCoreVideoPlayer from 'vue-core-video-player'

// global styles
import './assets/main.css'
import './assets/base.scss'
import './assets/styles/_cancel.scss'

Vue.config.productionTip = false

Vue.component('VuePicker', VuePicker)
Vue.component('VuePickerOption', VuePickerOption)
Vue.component('star-rating', StarRating)
Vue.use( VueCoreVideoPlayer)
Vue.use( VueTheMask)
Vue.component('date-picker', DatePicker)

axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('token')
axios.defaults.baseURL = "http://178.18.248.183/api/"


import GAuth from 'vue-google-oauth2'
const gauthOption = {
  clientId: '693702680980-6kbiik1n8k60fjl0n204lm58fhlquh94.apps.googleusercontent.com',
  scope: 'email',
  prompt: 'select_account'
}
Vue.use(GAuth, gauthOption)

import { VueEditor } from "vue2-editor";

Vue.component('text-editor', VueEditor)
// import { VueEditor, Quill } from "vue2-editor";

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
