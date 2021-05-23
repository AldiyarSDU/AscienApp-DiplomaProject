<template>
  <div class="instructor_courses p_page">
    <div class="container">
      <div class="teacher_title">
        Курсы
        <router-link :to="{name: 'UploadCourse'}" class="btn btn_gray">Create new course</router-link>

      </div>
      <div class="row" v-if="length !== 0">
        <div class="col-xl-3 mb-3" v-for="course in teacher_courses" :key="course.id">
            <router-link :to="{ name: 'UploadLessons', params: {id: course.id}}">
              <div class="course_items_card">
                <div class="course_card_img">
                  <img :src="'https://ascien.s3.us-east-2.amazonaws.com/' + course.image" alt="">
                </div>
                <p class="course_name">{{course.title}}</p>
<!--                <p class="end" v-if="course.status === 'processing'">Закончите этот курс</p>-->
                <div class="card_action">
                  <router-link class="btn main_btn" :to="{ name: 'CourseDetails', params: { id: course.id }}">View Demo</router-link>
                </div>
              </div>
            </router-link>
        </div>
      </div>
      <div v-else>
        <h4>Нет курсов</h4>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "InstructorCourses",
  data() {
    return {
      teacher_courses: null,
      length: null
    }
  },
  async created() {
    await axios.get('teacher/courses/all')
    .then(res => {
      this.teacher_courses = res.data
      this.length = res.data.length
    })
  }
}
</script>

<style scoped lang="scss">
  .course_card_img {
    border: 1px solid #dddddd;
  }
  .course_name {
    color: #586DCD;
    font-weight: 600;
    margin: 8px 10px;
  }
  .end {
    background: crimson;
    color: #ffffff;
    text-align: center;
    padding: 6px;
  }
  .card_action {
    padding: 0 10px 10px;
    text-align: center;
    a {
      width: 100%;
      color: #04724d;
      font-weight: 600;
      &:hover {
        color: #FFFFFF;
      }
    }
  }
</style>