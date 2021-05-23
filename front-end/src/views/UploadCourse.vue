<template>
  <div class="upload_course p_page">
    <div class="container">
      <div class="links_title">
        <router-link to="/">Главная</router-link>
        <img src="@/assets/icon/arrow.svg" alt="">
        <router-link :to="{ name: '' }">Предподователь</router-link>
        <img src="@/assets/icon/arrow.svg" alt="">
        <router-link :to="{ name: 'InstructorCourses' }">Мои курсы</router-link>
        <img src="@/assets/icon/arrow.svg" alt="">
        <router-link :to="{ name: 'UploadCourse' }">Загрузить курс</router-link>
      </div>
      <div class="teacher_title mt-4">
        Загрузить курс
      </div>
      <div class="upload_course_content">
        <form @submit.prevent="handleUpload" enctype="multipart/form-data">
          <div class="input">
            <label>Заголовок курса</label>
            <input v-model="title" type="text" placeholder="Вставьте заголовок курса.">
          </div>
          <div class="input">
            <label>Подзаголовок курса</label>
            <input v-model="subtitle" type="text" placeholder="Вставьте подзаголовок курса.">
          </div>
          <div class="input">
            <label>Описание курса</label>
            <text-editor v-model="description"></text-editor>
          </div>
          <div class="input">
            <label>Основная информация</label>
            <div class="row">
              <div class="col-xl-3">
                <vue-picker class="w-100" v-model="current">
                  <vue-picker-option v-for="lan in lang" :key="lan.language" :value="lan.language">{{lan.language}}</vue-picker-option>
                </vue-picker>
              </div>
              <div class="col-xl-3">
                <vue-picker class="w-100" v-model="level" placeholder="-- Выберите уровень --">
                  <vue-picker-option v-for="l in levels" :key="l.lev" :value="l.lev">{{l.lev}}</vue-picker-option>
                </vue-picker>
              </div>
              <div class="col-xl-3">
                <vue-picker class="w-100" v-model="c" placeholder="-- Выберите категорию --">
                  <vue-picker-option v-for="category in categories" :key="category.name" :value="category.id.toString()">{{category.name}}</vue-picker-option>
                </vue-picker>
              </div>
              <div class="col-xl-3">
                <input type="text" placeholder="Чему вы научитесь" v-model="what_will_learn">
              </div>
            </div>
          </div>

          <div class="input">
            <label>Необязательные поля</label>
            <div class="row">
              <div class="col-xl-4">
                <input type="text" placeholder="Требования">
              </div>
              <div class="col-xl-4">
                <vue-picker class="w-100" v-model="price_type" autofocus>
                  <vue-picker-option v-for="p in price" :key="p.title" :value="p.title">{{p.title}}</vue-picker-option>
                </vue-picker>
              </div>
              <div class="col-xl-4">
                <input type="text" :disabled="price_type === 'Бесплатно'" v-model="price_model">
              </div>
            </div>
          </div>
          <div class="image">
            <div class="row">
              <div class="col-xl-6">
                <img :src="imageUrl" alt="">
              </div>
              <div class="col-xl-6">
                <p>Изображение курса</p>
                <input type="file" @change="previewFiles">
              </div>
            </div>
          </div>
          <div class="image">
            <div class="row">
              <div class="col-xl-6">
                <img src="https://www.udemy.com/staticx/udemy/images/course/750x422/placeholder.png" alt="">
              </div>
              <div class="col-xl-6">
                <p>Рекламное видео</p>
                <input type="file" @change="videoFiles">
              </div>
            </div>
          </div>
          <button class="btn btn_gray">Загрузить</button>
        </form>
      </div>
    </div>
    <div class="loading" v-if="loading">
      <div class="lds-roller"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "UploadCourse",
  data () {
    return {
      categories: null,
      title: '',
      loading: false,
      subtitle: '',
      imageUrl: 'https://www.udemy.com/staticx/udemy/images/course/750x422/placeholder.png',
      image: null,
      video: null,
      current: 'English',
      c: undefined,
      level: undefined,
      price_model: '',
      description: '',
      what_will_learn: '',
      lang: [
        {language: 'English'},
        {language: 'Русский'},
      ],
      levels: [
        {lev: 'Начальный уровень'},
        {lev: 'Средний уровень'},
        {lev: 'Профессиональный уровень'},
        {lev: 'Все уровни'},
      ],
      price_type: 'Бесплатно',
      price: [
        { title: 'Бесплатно'},
        { title: 'Платный'},
      ]
    }
  },
  async created() {
    await axios.get('courses/getOnlyCategories')
    .then(res => {
      this.categories = res.data
    })
  },
  methods: {
    async handleUpload() {
      this.loading = true
      const formData = new FormData()

      formData.append('image', this.image)
      formData.append('category_id', this.c)
      formData.append('description', this.description)
      formData.append('language', this.current)
      formData.append('level', this.level)
      formData.append('title', this.title)
      formData.append('short_description', this.subtitle)
      formData.append('intro_video', this.video)
      formData.append('what_will_learn', this.what_will_learn)

      await axios.post('teacher/courses/store', formData)
      .then(res => {
        this.loading = false
        this.$router.push({
          name: 'UploadLessons',
          params: { id: res.data.data.course.id }
        })
      })

      if (this.level !== undefined && this.c !== undefined) {
        console.log(this.c)
        console.log(this.description)
        console.log(this.video)
      }
    },
    previewFiles(event) {
      let file = event.target.files[0]
      this.image = file
      this.imageUrl = this.getObjectURL(file)
    },
    videoFiles (e) {
      this.video = e.target.files[0]
    },
    getObjectURL (file) {
      let url = null ;
      if (window.createObjectURL!==undefined) { // basic
        url = window.createObjectURL(file) ;
      }else if (window.webkitURL!==undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file) ;
      }else if (window.URL!==undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file) ;
      }
      return url ;
    },
  },
  watch: {
    price_type() {
      this.price_model = ''
    }
  }
}
</script>

<style lang="scss" scoped>

</style>