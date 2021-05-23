<template>
  <div class="course_item">
    <router-link :to="{ name: 'CourseDetails', params: { id: course.id }}">
      <div class="row">
        <div class="col-xl-4">
          <div class="course_image">
            <img :src="'https://ascien.s3.us-east-2.amazonaws.com/' + course.image" alt="img">
          </div>
        </div>
        <div class="col-xl-6">
          <div class="course_title">
            <p>{{ course.title }}</p>
          </div>
          <div class="description">
            <p>{{ course.short_description }}</p>
          </div>
          <div class="author">
            <p>{{course.instructor.name}}</p>
          </div>
          <div class="rating">
            <span>{{rating}}</span>
            <star-rating class="star_rating" v-model="rating" v-bind="setting"></star-rating>
            <span>(1 235)</span>
          </div>
          <div class="time">
            <p>15 лекции, *{{course.level}}</p>
          </div>
        </div>
        <div class="col-xl-2">
          <div class="price_left">
            <p v-if="course.price">{{course.price}}	₸ <span class="old_price" v-if="course.sale_price">{{ course.sale_price }} ₸</span></p>
            <p v-else>Бесплатно</p>
          </div>
        </div>
      </div>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "CourseItem",
  props: ['course'],
  data() {
    return {
      rating: 4.5,
      setting: {
        starSize: 20,
        increment: 0.5,
        showRating: false,
        readOnly: true
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  .course_item {
    border-bottom: 1px solid #ddd;
    padding: 20px 0;
    cursor: pointer;
    &:hover {
      background: #f8f8f8;
    }
    .course_image{
      max-width: 300px;
      img {
        width: 100%;
        height: 25vh;
      }
    }
    .time p{
      font-size: 14px;
      color: #5b5b5b !important;
      margin: 5px 0;
    }
    a {
      text-decoration: none;
      color: #181818;
    }
    .star_rating {
      margin-top: -5px;
      margin-left: 10px;
    }
  }
</style>