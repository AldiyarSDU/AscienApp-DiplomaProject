<template>
  <div class="profile_setting p_page">
    <div class="container">
      <div class="teacher_title">
        Профили и настройки
      </div>
      <div class="profile_setting_content">
        <form @submit.prevent="updateTeacher">
        <div class="row">
          <div class="col-xl-6">
            <div class="input">
              <p>Имя</p>
              <input type="text" :value="name">
            </div>
          </div>
          <div class="col-xl-6">
            <div class="input">
              <p>E-mail</p>
              <input type="text" v-model="email">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-xl-3">
            <div class="input">
              <p>Дата рождения</p>
              <date-picker v-model="time1" valueType="format"></date-picker>
            </div>
          </div>
          <div class="col-xl-3">
            <div class="input">
              <p>Язык</p>
              <vue-picker class="w-100" v-model="current" autofocus>
                <vue-picker-option v-for="lan in lang" :key="lan.language" :value="lan.language">{{lan.language}}</vue-picker-option>
              </vue-picker>
            </div>
          </div>
          <div class="col-xl-6">
            <div class="input">
              <p>Номер телефона</p>
              <the-mask :mask="['+7(###) ###-##-##']" v-model="phone"/>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-xl-6">
            <div class="input">
              <p>Специальность</p>
              <input type="text" v-model="speciality">
            </div>
          </div>
          <div class="col-xl-6">
            <div class="input">
              <p>Facebook</p>
              <input type="text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-xl-6">
            <div class="input">
              <p>Образование</p>
              <input v-model="education" type="text">
            </div>
          </div>
          <div class="col-xl-6">
            <div class="input">
              <p>Инстаграм</p>
              <input type="text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-xl-6">
            <div class="input">
              <p>Опыт преподавания</p>
              <input type="text">
            </div>
          </div>
          <div class="col-xl-6">
            <div class="input">
              <p>Linkedin</p>
              <input type="text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-xl-6">
            <div class="input">
              <p>Достижения</p>
              <input type="text">
            </div>
          </div>
          <div class="col-xl-6">
            <div class="input">
              <p>YouTube</p>
              <input type="text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-xl-6">
            <div class="input">
              <p>О себя</p>
              <textarea></textarea>
            </div>
          </div>
          <div class="col-xl-6">
            <div class="input">
              <div class="action_profile">
                <button class="btn btn_gray">Продолжить</button>
              </div>
            </div>
          </div>
        </div>
        </form>
      </div>
      <pre>{{ GET_ANSWERS }}</pre>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import {mapGetters} from "vuex";
export default {
  name: "Profile",
  data() {
    return {
      current: 'English',
      time1: null,
      user: null,
      education: '',
      phone: '',
      speciality: '',
      email: '',
      name: '',
      lang: [
        {language: 'English'},
        {language: 'Русский'},
      ]
    }
  },
  async created() {
    await axios.get('user/info')
    .then(res => {
      this.email = res.data.data.user.email
      this.name = res.data.data.user.name
    })
    .catch(({response}) => {
      let user = response.data.validations.user
      this.email = user.email
      this.name = user.name
    })
  },
  methods : {
    async updateTeacher () {
      await axios.post('profile/teacher/update', {
        education_format: this.GET_ANSWERS[0],
        videomakeing_experience: this.GET_ANSWERS[1],
        auditory: this.GET_ANSWERS[2],
        education: this.education,
        phone: this.phone,
        specialty: this.speciality
      })
      .then(res => {
        this.user = res.data.data.user.teacher_profile
        this.education = this.user.education
        this.phone = this.user.contacts.phone
        this.speciality = this.user.contacts.speciality
      }).catch(({response}) => {
            console.log(response)
          })

    }
  },
  computed: {
    ...mapGetters([
        'GET_ANSWERS'
    ])
  }

}
</script>

<style lang="scss" scoped>
  .profile_setting {
    .profile_setting_content {
      p {
        margin-bottom: 0;
        color: #5b5b5b;
        font-size: 15px;
      }
      .mx-datepicker {
        width: 100%;
      }
      input, textarea {
        width: 100%;
        padding: 8px 20px !important;
        border: 1px solid #757575;
        border-radius: 4px;
      }
      .action_profile {
        text-align: right;
        margin-top: 30px;
      }
      .input {
        margin: 10px 0;
      }
    }
  }
</style>

<!--.then(res => {
      if (response.data.data.user !== null) {
        this.email = response.data.data.user.email
        this.name = response.data.data.user.name
      }
    }).catch(({response}) => {
          if (response.data.data.user !== null) {
            this.email = response.data.data.user.email
            this.name = response.data.data.user.name
          }
        })-->