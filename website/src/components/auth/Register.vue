<template>
  <div class="auth">
    <div class="form">
      <form @submit.prevent="registerHandler">
          <div class="inpt">
            <input v-model="username" type="text" placeholder="Username">
            <input v-model="email" type="email" placeholder="Email">
            <input v-model="password" type="password" placeholder="Password">
          </div>
          <label class="checkbox_custom">
            <input v-model="isTeacher" type="checkbox">
            <span>Как учитель</span>
          </label>
          <div class="action">
            <button class="main_btn">Регистрация</button>
          </div>
      </form>
      <button @click="signInWithGoogle" class="bnt btn_google" :disabled="!isTeacher">
        <img :src="require('../../assets/icon/google.svg')" alt="">
        <span class="google">Вход через <span class="google_text">Google</span></span>
      </button>
    </div>
    <transition name="toast">
      <Error :message="error" v-if="error"/>
    </transition>

  </div>
</template>

<script>
import Error from "@/components/Error";
import axios from "axios";
export default {
  name: "Register",
  components: {Error},
  data() {
    return {
      isTeacher: false,
      username: '',
      email: '',
      password: '',
      success: true,
      error: ''

    }
  },
  methods: {
    async registerHandler() {
      await axios.post('user/register', {
        name: this.username,
        email: this.email,
        password: this.password,
        is_teacher: this.isTeacher
      }).then(res=> {
        localStorage.setItem('email', res.data.data.user.email)
        // this.$router.push({ name: 'VerifyEmail' })
      })
      .catch(err => {
        if (err.response) {
          this.error = err.response.data.validations
          setTimeout(() => this.error = null, 3000)
        }
      })
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
        this.$router.push({
          name: 'Profile'
        })

      } catch (err) {
        console.log(err)
        return null
      }
    }
  },
}
</script>

<style lang="scss" scoped>

</style>