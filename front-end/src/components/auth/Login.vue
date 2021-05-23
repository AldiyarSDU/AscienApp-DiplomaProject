<template>
  <div class="auth">
    <div class="form">
      <form @submit.prevent="loginHandler">
          <div class="inpt">
            <input v-model="email" type="email" placeholder="Email" required>
            <input v-model="password" type="password" placeholder="Password" required>
          </div>
        <div class="forgot_pass">
          <a href="https://api.ascien.xyz/password/reset" target="_blank">
            <label class="forgot">Forgot Password?</label>
          </a>
        </div>
          <div class="action">
            <button class="main_btn">Войти</button>
          </div>
      </form>
      <button @click="signInWithGoogle" class="bnt btn_google">
        <img :src="require('../../assets/icon/google.svg')" alt="">
        <span class="google">Вход через <span class="google_text">Google</span></span>
      </button>
    </div>
    <transition name="toast">
      <div class="toast-wrapper" v-if="error">{{ error }}</div>
    </transition>
  </div>
</template>

<script>
import Error from "@/components/Error";
import axios from "axios";
import {mapActions, mapGetters} from "vuex";
export default {
  name: "Login",
  components: {Error},
  data() {
    return {
      email: '',
      password: '',
      error: '',
    }
  },
  methods: {
    ...mapActions([
        'USER_ACTION'
    ]),
    async loginHandler() {
      try {
        const response = await axios.post('user/login', {
          email: this.email,
          password: this.password,
        })

        localStorage.setItem('token', response.data.data.token)

        await this.$router.push({
          name: 'Profile'
        })
        location.reload()
        console.log(response.data)
      }
      catch (error) {
        console.log(error.response)
        let data = error.response.data
        if (data.data !== null) {
          let user = data.data.user

          localStorage.setItem('token', data.data.token)

          this.USER_ACTION(user)

          if (!user.is_completed && user.is_teacher) {
            this.$router.push({
              name: 'InstructorPage'
            })
          }
        }
        this.error = data.message

      }
      setTimeout(() => this.error = '', 3000)
    },
    async signInWithGoogle () {
      try {
        const googleUser = await this.$gAuth.signIn()
        if (!googleUser) {
          return null
        }
        await axios.post('user/google/auth', {
          name: googleUser.getBasicProfile().getGivenName(),
          email: googleUser.getBasicProfile().getEmail(),
          google_id: googleUser.getId()
        })
        .then(res => {
          localStorage.setItem('token', JSON.stringify(res.data.data.token))
          this.$router.push({
            name: 'Home'
          })
        })

      }catch (err) {
        console.log(err)
        return null
      }
    }
  },
  computed: {
    ...mapGetters([
        'GET_USER'
    ])
  }
}
</script>

<style lang="scss" scoped>

</style>