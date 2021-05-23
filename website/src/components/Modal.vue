<template>
  <div class="modal" @click.self="showModal">
    <div class="modal_content">
      <div class="tab">
        <button :class="{ active: currentPage === 'Login'}" @click="current('Login')">Вход</button>
        <button :class="{ active: currentPage === 'Register'}" @click="current('Register')">Регистрация</button>
      </div>
      <component :is="currentPage" />
    </div>
  </div>
</template>

<script>
import Login from "@/components/auth/Login";
import Register from "@/components/auth/Register";
import {mapGetters} from "vuex";
export default {
  name: "Modal",
  data() {
    return {
      currentPage: 'Login'
    }
  },
  components: {Register, Login},
  methods: {
    showModal() {
      this.$store.state.showModal = !this.$store.state.showModal
    },
    current(by) {
      this.currentPage = by
    }
  },
  mounted() {
    if (this.getModal) {
      document.body.style.overflowY = 'hidden'
    }
  },
  destroyed() {
    if (!this.getModal) {
      document.body.style.overflowY = 'auto'
    }
  },
  computed: {
    ...mapGetters([
       'GET_USER'
    ]),
    getModal() {
      if (this.GET_USER) {
        this.$store.state.showModal = false
      }
      return this.$store.state.showModal
    }
  }
}
</script>

<style lang="scss" >
  .modal {
    position: fixed;
    width: 100%;
    z-index: 99;
    display: flex;
    .modal_content {
      max-width: 400px;
      background: #FFFFFF;
      margin: 40px auto;
      color: #181818;
    }
  }
  .tab {
    display: flex;
    button {
      width: 100%;
      padding: 16px 0;
      border: transparent;
      background: transparent;
      border-bottom: 2px solid transparent;
      &:hover {
        border-bottom: 2px solid #04724D;
        transition: all 0.5s ease;
        color: #04724D;
      }
    }
  }

  button.active {
    background: #04724D;
    color: #FFFFFF !important;
  }
</style>