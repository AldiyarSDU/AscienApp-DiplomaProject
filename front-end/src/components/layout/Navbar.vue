<template>
  <div class="nav">
    <div v-if="getModal" class="bg"></div>
    <div @click="show_modal" v-if="getModal" class="cancel">
      <span></span>
    </div>
    <transition name="modal">
      <Modal v-if="getModal"/>
    </transition>
    <div class="container">
      <nav>
        <div class="row header">
          <div class="col-xl-1 col-md-1">
            <router-link to="/">
              <img class="logo" src="../../assets/logo.svg" alt="logo">
            </router-link>
          </div>
          <div class="col-xl-5 col-md-4">
            <form @submit.prevent="searchCourses">
              <img src="../../assets/icon/search_icon.svg" alt="">
              <input type="text" placeholder="Введите запрос" v-model="search">
            </form>
          </div>
          <div class="col-xl-5 col-md-7">
            <div class="links_header">
              <div class="lang">
                <p><span class="green_text">RU</span> KZ</p>
              </div>
              <router-link v-if="user" to="/instructor/courses">Мои курсы</router-link>
              <router-link to="/about">О нас</router-link>
              <router-link to="/help">Помощь</router-link>
              <button @click="show_modal" class="main_btn" v-if="!user">Войти</button>
              <router-link to="/logout" v-if="user">
                <div class="avatar" >
                  <p>PP</p>
                </div>
              </router-link>
            </div>
          </div>
        </div>
      </nav>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import Modal from "@/components/Modal";
import axios from "axios";

export default {
  name: "Navbar",
  data() {
    return {
      search: '',
      user: null
    }
  },
  watch: {
    $route(to, from) {
      this.$store.state.showModal = false
      this.$forceUpdate()
      this.search = ''
      axios.get('user/info')
      .then(res => {
        console.log(res.data)
        this.user = res.data.data.user
      })
      .catch(({response}) => {
        console.log(response)
        this.user = response.data.validations.user
      })
    }
  },
  components: {Modal},
  methods: {
    show_modal() {
      this.$store.state.showModal = !this.$store.state.showModal
    },
    searchCourses () {
      this.$router.push({
        name: 'Courses',
        params: { slug: this.search}
      })
    }
  },
  computed: {
    ...mapGetters([
        'GET_USER',
    ]),
    getModal() {
      return this.$store.state.showModal
    },
  },
  async mounted() {
    axios.get('user/info')
        .then(res => {
          this.user = res.data.data.user
        })
        .catch(({response}) => {
          this.user = response.data.validations.user
        })
  },
}
</script>
<style lang="scss" scoped>
  @import "../../assets/variables";

  .nav {
    position: sticky;
    top: 0;
    z-index: 10;
    background: #FFFFFF;
  }

  .logo {
    max-width: 100px;
    width: 100%;
  }
  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  nav {
    padding: 10px 0;

    form {
      width: 100%;
      position: relative;
      display: flex;
      align-items: center;
      input {
        width: 100%;
        background-color: #ededed;
        border-radius: 30px;
        padding: 8px 16px 10px 50px;
        border: none;
        outline: none;
      }
      img {
        position: absolute;
        left: 16px;
      }
    }
    .links_header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding-left: 40px;
    }
    .links_header a{
      text-decoration: none;
      font-weight: 600;
      color: #5b5b5b;
      &:hover {
        color: #000000;
      }
    }
  }
  a.router-link-active {
    color: #04724D !important;
    //background: #181818;
  }
  .close {
    position: absolute;
    right: 1%;
    top: 10%;
    cursor: pointer;
    color: #FFFFFF;
    z-index: 10000;
  }
  .avatar {
    max-width: 50px;
    cursor: pointer;
    border-radius: 50px;
    background: crimson;
    p{
      display: flex;
      justify-content: center;
      align-items: center;
      margin-bottom: 0;
      color: white;
      font-weight: 600;
      width: 50px;
      height: 50px;
      font-size: 18px;
    }

    img {
      width: 100%;
      height: 50px;
      object-fit: cover;
      border-radius: 50px;
    }
  }

  .modal-enter, .modal-leave-to {
    opacity: 0;
    transform: translateY(-100%);
  }
  .modal-enter-active, .modal-leave-active {
    transition: all 0.4s ease;
  }
</style>