<template>
  <div>
    <div class="upload_lessons p_page" :class="{ load: loading }">
      <div class="container">
        <div class="teacher_title">
          Загрузить урок
        </div>
        <p>Начните составлять свой курс, создав разделы и лекции</p>

        <h3>{{ get_course.title}}</h3>

        <p @click="back" class="go_back"> < Назад </p>
        <div class="section_content">
          <div v-for="(section, index) in get_course.sections" :key="index">
            <div class="section_wrapper">
              <div class="section_header">
                <span class="mr-2 section_num">Часть {{index + 1}}:</span>
                <span class="section_icon mr-1"><i class="fas fa-file-alt"></i></span>
                <span>{{section.title}}</span>
                <button class="btn btn_edit">
                  <span class="edit_icon"><i class="fas fa-pencil-alt"></i></span>
                </button>
                <button class="btn btn_delete" @click="deleteSection(section.id)">
                  <span class="edit_icon"><i class="fas fa-trash-alt"></i></span>
                </button>
              </div>
              <div v-for="(lesson, idx) in section.lessons" :key="lesson.id">
                <div class="lesson_item_content">
                  <div class="section_header">
                    <span class="mr-2 section_num">Лекция {{ index +1 + idx }}:</span>
                    <span class="section_icon mr-1"><i class="fas fa-file-alt"></i></span>
                    <span>{{lesson.title}}</span>
                    <button class="btn btn_edit">
                      <span class="edit_icon"><i class="fas fa-pencil-alt"></i></span>
                    </button>
                    <button class="btn btn_delete" @click="deleteLesson(lesson.id)">
                      <span class="edit_icon"><i class="fas fa-trash-alt"></i></span>
                    </button>
                  </div>
                  <div class="item_content">
                    <p>{{ lesson.content_type }}</p>
                    <p>{{ lesson.created_at}}</p>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-xl-1">
                  <div class="add_content">
                    <span class="add_icon" @click="createLesson(index)" :class="{ active_btn: q === index }"></span>
                  </div>
                </div>
                <div class="col-xl-11">
                  <div class="create_lesson" v-if="q === index">
                    <div class="create_section">
                      <div class="row">
                        <div class="col-xl-2">
                          <div class="section_title">
                            <p>Новый урок: </p>
                          </div>
                        </div>
                        <div class="col-xl-10">
                          <input type="text" placeholder="Введите название" v-model="titleLesson">
                          <div class="lesson_contents">
                            <button
                                class="btn_content"
                                @click="handleContent('video')"
                                :class="{ activeContent: currentContent === 'video'}"
                            >Видео</button>
                            <button
                                class="btn_content"
                                @click="handleContent('article')"
                                :class="{ activeContent: currentContent === 'article'}"
                            >Статья</button>
                          </div>
                          <div class="lesson_content" v-if="currentContent === 'video'">
                            <input type="file" @change="videoUrl = $event.target.files[0]">
                          </div>
                          <div class="lesson_content" v-if="currentContent === 'article'">
                            <text-editor v-model="article"></text-editor>
                          </div>
                          <div v-if="errorLesson" v-for="err in errorLesson" >
                            <p class="error" v-for="e in err">{{ e}}</p>
                          </div>

                          <div class="section_action">
                            <button @click="cancel" class="btn btn_cancel">Отменить</button>
                            <button @click="addCourse(section.id)" class="btn btn_primary">Добавить курс</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="add_content">
            <span class="add_icon" @click="createSection" :class="{ active_btn: new_section }"></span>
            <span> Добавить раздел</span>
          </div>
          <div>
            <div class="create_section" v-if="new_section">
              <div class="row">
                <div class="col-xl-2">
                  <div class="section_title">
                    <p>Новый раздел: </p>
                  </div>
                </div>
                <div class="col-xl-10">
                  <input type="text" placeholder="Введите название" v-model="course_name">
                  <p class="error" v-if="error.length">{{error}}</p>
                  <div class="section_subtitle">
                    <p>Что студенты смогут делать в конце этого раздела?</p>
                    <input type="text" placeholder="Войти в Цели обучения">
                  </div>
                </div>
              </div>
              <div class="section_action">
                <button @click="createSection" class="btn btn_cancel">Отменить</button>
                <button @click="addSection" class="btn btn_primary">Добавить раздел</button>
              </div>
            </div>
          </div>
        </div>
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
  name: "UploadLessons",
  data() {
    return {
      new_section: false,
      course_name: '',
      get_course: null,
      error: '',
      q: -1,
      currentContent: 'video',
      article: '',
      titleLesson: '',
      id: this.$route.params.id,
      videoUrl: null,
      errorLesson: '',
      loading: false
    }
  },
  watch: {
    $route (to, from){
      this.loading = true
       axios.get('teacher/courses/get/' + this.$route.params.id)
          .then(res => {
            this.get_course = res.data
            console.log(res.data)
            this.loading = false
          })
    }
  },
  methods: {
    back() {
      this.$router.go(-1)
    },
    handleContent(type){
      this.currentContent = type
      if (type === 'video'){
        this.article = ''
      }
    },
    cancel () {
      this.q = -1
    },
    createSection() {
      this.new_section = !this.new_section
      this.course_name = ''
      this.error = ''
    },
    async addSection() {
      await axios.post('teacher/courses/create/section', {
        title: this.course_name,
        course_id: this.id
      }).then((res) => {
        this.get_course = res.data.data.course
        this.error = ''
        this.new_section = false
      }).catch(res => {
        this.error = res.response.data.validations.title[0]
      })
    },
    async addCourse(id) {
      this.loading = true

      if (this.loading) {
        document.body.style.overflowY = 'hidden'
      }
      this.errorLesson = ''
      let formData = new FormData()
      formData.append("title", this.titleLesson)
      formData.append("section_id", id)
      formData.append("course_id", this.id)
      formData.append("content_type", this.currentContent)
      if (this.currentContent === 'video') {
        formData.append("video_url", this.videoUrl)
      }
      if (this.currentContent === 'article') {
        formData.append("article_text", this.article)
      }
      await axios.post('teacher/courses/create/lesson', formData)
      .then(res => {
        this.get_course = res.data.data.course
        this.q = -1
        this.loading = false
        document.body.style.overflowY = 'auto'
      }).catch(res => {
        this.loading = false
            document.body.style.overflowY = 'auto'
            this.errorLesson = res.response.data.validations
          })
    },
    async deleteSection(id) {
      await axios.post('teacher/courses/delete/section/' + id)
      .then((res) => {
        location.reload()
        // this.get_course = res.data.data.course
      })
    },
    async deleteLesson (id) {
      await axios.post('teacher/courses/delete/lesson/' + id)
      .then(res => {
        location.reload()
      })
    },
    createLesson (index) {
      this.article = ''
      this.currentContent = 'video'
      this.titleLesson = ''
      if (this.q === index) {
        this.q = null
      }else {
        this.q = index
      }
    }
  },
  async mounted() {
    await axios.get('teacher/courses/get/' + this.id)
    .then(res => {
      this.get_course = res.data
      console.log(res.data)
    })
  },
}
</script>

<style scoped lang="scss">
  .lesson_contents {
    display: flex;
    justify-content: center;
    border-bottom: 1px solid #5b5b5b;
    margin: 20px 0;
    .btn_content {
      margin: 0 10px;
      border-radius: 0;
      font-weight: 600;
      font-size: 20px;
      color: #757575;
      border: transparent;
      background: transparent;
      border-bottom: 3px solid transparent;
      margin-bottom: -2px;
      transition: 0.2s ease;
    }
    .activeContent {
      color: black;
      border-bottom: 3px solid #04724d;
    }
  }
  .lesson_content {
    margin: 10px 0;
  }
  .lesson_item_content {
    margin-left: 100px;
    background: white;
    padding: 10px;
    margin: 20px 0;
    border: 1px solid #757575;
  }
  .section_header {
    border-bottom: 1px solid #757575;
    margin: 10px 0;
  }
  .item_content {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .go_back {
    cursor: pointer;
    color: crimson;
    font-size: 18px;
    font-weight: 600;
  }
</style>