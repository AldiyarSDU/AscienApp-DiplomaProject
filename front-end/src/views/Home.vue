<template>
  <div class="home">
    <div class="main_content">
      <div class="banner_image"></div>
      <div class="container">
        <div class="banner_text">
          <p class="green_text">Станьте учителем <span class="big_text">БЕСПЛАТНО</span></p>
          <button class="main_btn">Начинай сейчас</button>
        </div>
      </div>
    </div>
    <div class="course_content">
      <div class="container">
        <div class="title_page">
          <h2>Популярные <span class="green_text">курсы</span></h2>
        </div>
        <VueSlickCarousel v-if="courses" class="slider" v-bind="settings">
          <div class="slide" v-for="course in courses"
            :key="course.id"
          >
            <router-link :to="{ name: 'CourseDetails', params: { id: course.id }}">
              <img :src="'https://ascien.s3.us-east-2.amazonaws.com/' + course.image" alt="img">
              <div class="course_title">
                <p>{{ course.title }}</p>
              </div>
              <div class="author">
                <p>{{course.instructor.name}}</p>
              </div>
              <div class="rating">
                <span>{{rating}}</span>
                <star-rating class="star_rating" v-model="rating" v-bind="setting"></star-rating>
                <span>(1 235)</span>
              </div>
              <div class="price_inline" v-if="course.price">
                <p>{{course.price}} ₸ <span class="old_price" v-if="course.sale_price"> {{ course.sale_price}}</span></p>
              </div>
            </router-link>
          </div>
        </VueSlickCarousel>
      </div>
    </div>
<!--    <div class="recommend_course_content">-->
<!--      <div class="container">-->
<!--        <div class="title_page">-->
<!--          <h2>Рекомендуемые <span class="green_text">курсы</span></h2>-->
<!--        </div>-->
<!--        <div v-for="course in courses" :key="course.id">-->
<!--          <CourseItem :course="course"/>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
  </div>
</template>

<script>
import VueSlickCarousel from 'vue-slick-carousel'
import 'vue-slick-carousel/dist/vue-slick-carousel.css'
// optional style for arrows & dots
import 'vue-slick-carousel/dist/vue-slick-carousel-theme.css'
import CourseItem from "@/components/course/CourseItem";
import { mapGetters } from 'vuex'
import axios from "axios";

export default {
  name: 'Home',
  components: {CourseItem, VueSlickCarousel },
  data: () => ({
    rating: 4.5,
    setting: {
      starSize: 18,
      increment: 0.5,
      showRating: false,
      readOnly: true
    },
    settings: {
      arrows: true,
      infinite: false,
      speed: 500,
      slidesToShow: 4,
      slidesToScroll: 1,
      initialSlide: 0,
          responsive: [
        {
          breakpoint: 1024,
          settings: {
            slidesToShow: 3,
            slidesToScroll: 3,
            infinite: true,
            dots: true
          }
        },
        {
          "breakpoint": 768,
          "settings": {
            "slidesToShow": 2,
            "slidesToScroll": 1,
          }
        },
        {
          "breakpoint": 576,
          "settings": {
            "slidesToShow": 1,
            "slidesToScroll": 1
          }
        }
      ]
    },
    courses: null

  }),
  computed: {
  },
  async created() {
    await axios.get('courses/getTopCourses')
    .then(res => {
      this.courses = res.data
      // console.log(res.data)
    })
  }
}
</script>

<style lang="scss">
  .home {
    .main_content {
      position: relative;

    }
    .banner_image {
      position: absolute;
      width: 100%;
      height: 100%;
      right: 0;
      top: 0;
      bottom: 0;
      background-repeat: no-repeat;
      background-size: cover;
      background-position-x: right;
      background-image: url("../assets/img/banner_image.png");
    }
    a {
      text-decoration: none;
      color: #181818;
    }
    .banner_text {
      position: relative;
      padding-top: 300px;
      bottom: 15vh;
      p {
        margin-bottom: 40px;
        font-size: 2.875em;
        line-height: 1;
        font-weight: 600;
        .big_text {
          display: block;
          font-weight: 800;
          font-size: 2.17em;
          color: #181818;
        }
      }
    }
  }
  .star_rating {
    margin-top: -7px;
    margin-left: 10px;
  }
  .course_content {
    position: relative;
    padding: 50px 0;
    .slider {
      .slide {
        cursor: pointer;
        &:hover {
          box-shadow: 1px 2px 6px rgba(0,0,0,0.3);
          filter: brightness(0.8);
        }
        padding: 0 10px;
        img {
          width: 100%;
          height: 20vh;
          object-fit: cover;
          border: 1px solid #e0e0e0;
        }
      }
      div.slick-list {
        max-width: 1080px;
        margin: 0 auto !important;
      }
      .slick-arrow {
        width: 40px;
        height: 40px;
        z-index: 5;
        top: 30%;
        //background: #04724D;
        //border-radius: 50px;
        &:before {
          display: none;
        }
        &.slick-prev {
          left: 0;
          background-size: cover;
          background-image: url("../assets/icon/prev.svg");

        }
        &.slick-next {
          right: 0;
          background-size: cover;
          background-image: url("../assets/icon/next.svg");
        }
      }
      .slick-disabled {
        //background: #dddddd !important;
        display: none !important;
      }
    }
  }

</style>