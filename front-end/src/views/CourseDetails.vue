<template>
  <div class="course_details p_page">
    <div class="container">
      <div class="links_title">
        <router-link to="/">Главная</router-link>
        <img src="@/assets/icon/arrow.svg" alt="">
        <router-link :to="{ name: 'CourseDetails' }">Курсы</router-link>
      </div>
      <div class="course_details_content" v-if="!loading">
        <div class="row">
          <div class="col-xl-7">
            <div class="course_video_content"
                 @mouseover="OVERLAY_ACTION"
                 @mouseleave="NOT_OVERLAY_ACTION"
            >
              <img
                  :src="'https://ascien.s3.us-east-2.amazonaws.com/' + course.image"
                  alt=""
                  @click="showVideoCourse"
                  :class="{ overlayActive: GET_OVERLAY }"
              >
              <i @click="showVideoCourse" class="fas fa-play-circle" v-if="GET_OVERLAY"></i>
            </div>
            <div class="course_title_content">
              <p class="b_text">{{course.title}}</p>
              <p class="sub_text">{{course.short_description}}</p>
              <div class="detail_wrapper">
                <div class="detail_wrapper_text">
                  <div class="what_will_learn">
                    <p class="title">Чему вы научитесь</p>
                    <span v-for="w in JSON.parse(course.what_will_learn)">{{ w }}</span>
                  </div>
                </div>
              </div>
              <div class="detail_wrapper">
                <div class="detail_wrapper_text">
                  <p class="title" v-if="course.requirements">Требования</p>
                  <span>{{ course.requirements}}</span>
                </div>
              </div>
              <div class="detail_wrapper">
                <div class="detail_wrapper_text">
                  <p class="title">Описание</p>
                  <span v-html="course.description"></span>
                </div>
              </div>
              <div class="detail_wrapper">
                <div class="detail_wrapper_text">
                  <div class="title_page">
                    <h2>Материалы <span class="green_text">курса</span></h2>
                  </div>
                  <course-video-content :items="course.sections"/>
                </div>
              </div>
              <div class="detail_wrapper">
                <div class="detail_wrapper_text">
                  <p class="title">Преподаватель</p>
                  <div class="teacher_content">
                    <div class="contact_info">
                      <p class="name">{{course.instructor.name}}</p>
                      <span>{{ course.instructor.email}}</span>
                    </div>
                    <div class="teacher_wrapper">
                      <img src="http://s3.amazonaws.com/37assets/svn/765-default-avatar.png" alt="">
                      <div class="info">
                        <div>
                          <div class="info_wrapper">
                            <i class="fas fa-star"></i>
                          </div>
                          <div class="info_wrapper">
                            <i class="fas fa-bookmark"></i>
                          </div>
                          <div class="info_wrapper">
                            <i class="fas fa-user-friends"></i>
                          </div>
                          <div class="info_wrapper">
                            <i class="fas fa-play-circle"></i>
                          </div>
                        </div>
                        <div class="info_wrapper_title">
                          <p>4.5 рейтинг</p>
                          <p>5 отзывов</p>
                          <p>3 студентов</p>
                          <p>1 курс</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="detail_wrapper">
                <div class="detail_wrapper_text">
                  <p class="title">Отзывы</p>
                  <div class="rating_content">
                    <span class="rating_big">4.5</span>
                    <star-rating v-model="rating" v-bind="setting"></star-rating>
                  </div>
                  <div class="title_page">
                    <h2>Отзывы <span class="green_text">студентов</span></h2>
                  </div>
                  <div class="reviews">
                    <review :reviews="reviews"/>
                  </div>
                </div>
            </div>
            </div>
          </div>
          <div class="col-xl-5">
            <div class="course_card">
              <div class="course_name">
                <p>{{ course.title }}</p>
              </div>
              <div class="rating">
                <span>4.5</span>
                <star-rating v-model="rating" v-bind="setting"></star-rating>
                <span>(11)</span>
              </div>
              <div class="price_course">
                <p v-if="course.price">{{ course.price }} kzt</p>
              </div>
              <div class="course_card_footer">
                <p><i class="fas fa-globe"></i> Язык: {{course.language}}</p>
                <p><i class="fas fa-layer-group"></i> Уровень: {{course.level}}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="bg" @click="showVideoCourse" v-if="GET_SHOW_VIDEO"></div>
    <div class="video_container">
      <video-modal v-if="GET_SHOW_VIDEO" :video="course"/>
    </div>
    <div class="loading" v-if="loading">
      <div class="lds-roller"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
    </div>
  </div>
</template>

<script>
import VueSlickCarousel from 'vue-slick-carousel'
import 'vue-slick-carousel/dist/vue-slick-carousel.css'
// optional style for arrows & dots
import 'vue-slick-carousel/dist/vue-slick-carousel-theme.css'
import { mapGetters, mapActions } from 'vuex'
import VideoModal from "@/components/VideoModal";
import axios from "axios";
import CourseVideoContent from "@/components/CourseVideoContent";
import Review from "@/components/Review";
export default {
  name: "CourseDetails",
  props: ['id'],
  navFix: false,
  components: {Review, CourseVideoContent, VideoModal, VueSlickCarousel },
  data() {
    return {
      loading: false,
      course: null,
      rating: 4.5,
      setting: {
        starSize: 25,
        increment: 0.5,
        showRating: false,
        readOnly: true
      },
      reviews: [
        {
          id: 1,
          name: 'Павел',
          rating: 5,
          review: 'Великолепный курс. Всё очень понятно и структурно. Даёт исчерпывающую информацию о мире криптовалют. Спасибо!'
        },
        {
          id: 2,
          name: 'Александр Васильев',
          rating: 3.5,
          review: 'Не удается зарегестрировать свой аккаунт.'
        },
        {
          id: 3,
          name: 'Денис',
          rating: 4,
          review: 'Спасибо. Курс мне подошел. Некоторая информация устарела'
        },
      ]
    }
  },
  beforeRouteUpdate(to, from, next) {
    console.log(to)
    next();
  },
  async mounted() {
    this.loading = true
    await axios.get('courses/getDetailPublicCourse/' + this.id)
    .then(res => {
      this.loading = false
      this.course = res.data
      console.log(res.data)
    })
  },
  methods : {
    ...mapActions([
        'SHOW_VIDEO_ACTION',
        'OVERLAY_ACTION',
        'NOT_OVERLAY_ACTION'
    ]),
    handleScroll: function () {
      let vm = this;
      window.onscroll = () => {
        if (window.scrollY > 0) {
          vm.navFix = true;
        } else {
          vm.navFix = false;
        }
      };
    },
    showVideoCourse () {
      this.SHOW_VIDEO_ACTION()
    }
  },
  computed: {
    ...mapGetters([
        'GET_SHOW_VIDEO',
        'GET_OVERLAY'
    ]),
  },
  updated() {
    if (this.GET_SHOW_VIDEO) {
      document.body.style.overflowY = 'hidden'
    }else {
      document.body.style.overflowY = 'auto'
    }
  },
  watch: {
    $route (to, from){
      this.loading = true
      axios.get('courses/getDetailPublicCourse/' + this.id)
          .then(res => {
            this.course = res.data
            this.loading = false
          })
    }
  }
}
</script>

<style lang="scss" scoped>
  .course_details_content {
    margin: 30px 0;
    .course_video_content {
      position: relative;
      img {
        width: 100%;
        cursor: pointer;
      }
      i {
        position: absolute;
        font-size: 48px;
        color: #FFFFFF;
        cursor: pointer;
        left: 48%;
        top: 44%;
      }
    }
    .course_card {
      position: sticky;
      top: 80px;
      border: 1px solid #757575;
      padding: 20px 30px;
      box-shadow: 1px 2px 3px rgba(0,0,0,0.5);
      border-radius: 5px;
      width: 85%;
      margin-left: auto;
      .course_name {
        p {
          font-weight: 500;
          font-size: 24px;
          color: #586DCD;
        }
      }
      .rating {
        font-size: 14px;
        p {
          margin-bottom: 0;
        }
      }
      .price_course {
        margin: 15px 0;
        font-size: 28px;
        color: #3E444A;
        font-weight: 700;
      }
    }
  }
  img.overlayActive {
    filter: brightness(0.6);
  }
  .teacher_content {
    border: 1px solid #757575;
    padding: 10px 20px;
    border-radius: 8px;
    box-shadow: 1px 2px 4px rgba(0,0,0,0.25);
    .name {
      font-size: 20px;
      font-weight: 800;
      color: #586DCD;
      margin-bottom: 0;
    }
    .teacher_wrapper {
      display: flex;
      align-items: center;
      img {
        border-radius: 50%;
        border: 1px solid #757575;
        max-width: 140px;
        max-height: 140px;
      }
      .info {
        display: flex;
        margin: 0 50px;
        .info_wrapper, .info_wrapper_title p {
          margin: 10px 5px;
          i {
            color: #ff9900;
          }
        }
        p {
          margin-bottom: 0;
        }
      }
    }
  }
  .rating_content {
    display: flex;
    flex-direction: column;
    align-items: center;
    .rating_big {
      font-size: 72px;
      font-weight: 700;
      margin-bottom: -20px;
    }
  }

  .detail_wrapper {
    margin: 20px 0;
  }

  .b_text {
    font-size: 28px;
    margin: 10px 0;
    font-weight: 600;
  }
  .sub_text {
    font-size: 20px;
    color: #757575;
    font-weight: 400;
  }
  .what_will_learn {
    background: #f6f6f6;
    border: 1px solid #757575;
    border-radius: 4px;
    padding: 10px 20px;
  }
  .contact_info {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin: 10px 0;
  }
  .course_card_footer {
    i {
      color: #04724d;
    }

  }
</style>