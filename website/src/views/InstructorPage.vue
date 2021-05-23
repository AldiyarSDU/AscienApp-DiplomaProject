<template>
  <div class="instructor p_page">
    <div class="container">
      <div class="teacher_title">
        Поделитесь своими знаниями
      </div>
      <div class="question" v-for="(qn, index) in questions" :key="qn.question">
        <p>{{ qn.question }}</p>
        <div class="row">
          <div class="col-xl-6">
            <div class="question_items" @click="handleNext(question.q, index)" v-for="question in qn.v" :key="question.id">
              <label :for="'answer' + question.id" :class="{ active: qn.answer === question.q}">
                <input type="radio" :value="question.q" v-model="qn.answer" :id="'answer' + question.id"> <span>{{ question.q }}</span></label>
            </div>
          </div>
        </div>
      </div>
      <div class="text-right">
        <router-link :to="{ name: 'Profile' }" class="btn btn_gray">Продолжить</router-link>
      </div>
    </div>
    <pre>{{ GET_ANSWERS }}</pre>
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
  name: "InstructorPage",
  data() {
    return {
      questions: [
        {
          question : 'В каком формате вы преподавали раньше?',
          answer: '',
          id: 1,
          v: [
            {
              id: 1,
              q: 'Лично, частным образом'
            },
            {
              id: 2,
              q: 'Лично, профессионально'
            },
            {
              id: 3,
              q: 'Онлайн'
            },
            {
              id: 4,
              q: 'Другое'
            }
          ]
        },
        {
          question: 'Насколько хорошо вы разбираетесь в видеозаписи?',
          answer: '',
          id: 5,
          v: [
            {
              id: 5,
              q: 'Я новичок'
            },
            {
              id: 6,
              q: 'У меня есть определенные знания'
            },
            {
              id: 7,
              q: 'У меня большой опыт'
            },
            {
              id: 8,
              q: 'У меня уже есть готовые видео для загрузки'
            }
          ]
        },
        {
          question: 'У вас есть аудитория для вашего курса?',
          answer: '',
          id: 9,
          v: [
            {
              id: 9,
              q: 'В настоящий момент нет'
            },
            {
              id: 10,
              q: 'У меня маленькая аудитория'
            },
            {
              id: 11,
              q: 'У меня достаточная аудитория'
            }
          ]
        }
      ]
    }
  },
  methods: {
    ...mapActions([
        'USER_ACTION'
    ]),
    handleNext(answer, index) {
      this.$store.commit('SET_ANSWERS', [index, answer])
    }
  },
  computed: {
    ...mapGetters([
        'GET_ANSWERS',
        'GET_USER'
    ])
  },
  created() {
    this.USER_ACTION()

    this.$router.beforeEach((to, from, next) => {
      if (to.matched.some(s => s.meta.completed)) {
        if (this.GET_USER.is_teacher && this.GET_USER.is_completed) {
          next({
            name: 'Profile'
          })
        }else {
          next()
        }
      }else {
        next()
      }
    })
  }
}
</script>

<style lang="scss" scoped>
  .question {
    margin: 30px 0;
  }
  .question_items {
    label {
      padding: 15px;
      width: 100%;
      border-radius: 4px;
      border: 1px solid #5b5b5b;
      cursor: pointer;
    }
    input[type='radio'] {
      display: none;
    }
  }
  .active {
    background: #e7ffff;
    box-shadow: 0 0 0.4px rgba(0,0,0,0.1);
  }
</style>