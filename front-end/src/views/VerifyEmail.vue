<template>
  <div class="verify_email">
    <transition name="success">
      <div class="success" v-if="message">
        {{ message }}
      </div>
    </transition>
    <div class="p_page">
      <div class="container">
        <div class="verify_message">
          <div>
            <p>На почту которую вы указали отправлена ссылка для подтверждения!</p>
            <p>Пожалуйста, подтвердите почту.</p>
          </div>
        </div>
      </div>
      <div class="resend_text">
        <span class="alert_text">Если вы не получили сообщение</span>
        <button @click="resendEmail" class="btn btn_gray">Отправить снова</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "VerifyEmail",
  data() {
    return {
      message: ''
    }
  },
  methods: {
    async resendEmail () {
      const response = await axios.post('user/verify/email/resend/' + localStorage.getItem('email'))

      this.message = response.data.message

      setTimeout(() => this.message = '', 3000)
    }

  }
}
</script>

<style lang="scss" scoped>
  .verify_message {
    border: 1px solid #5b5b5b;
    border-radius: 5px;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 100px;
    max-width: 600px;
    margin: 0 auto;
    p {
      margin-bottom: 0;
      font-weight: 600;
      font-size: 18px;
      text-align: center;
    }
  }
  .resend_text {
    padding: 50px 0;
    text-align: center;
    .alert_text {
      color: #586DCD;
      font-weight: 600;
      margin-right: 20px;
      text-decoration: underline;
    }
  }
  .success-enter, .success-leave-to {
    transform: translateY(-100px);
  }
  .success-enter-active, .success-leave-active {
    transition: all 0.3s ease;
  }
</style>