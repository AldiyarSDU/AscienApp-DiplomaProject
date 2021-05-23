<template>
  <div class="courses p_page">
    <div class="container">
      <div class="title_page">
        <h2>Result: <span class="green_text">{{ this.$route.params.slug }}</span></h2>
      </div>
      <div class="row">
        <div class="col-xl-3">
            <FilterContent />
        </div>
        <div class="col-xl-9">
          <div v-for="course in courses" :key="course.id" v-if="course">
            <CourseItem :course="course"/>
          </div>
          <div v-else>Noting else</div>
        </div>
      </div>
    </div>
    <div class="loading" v-if="loading">
      <div class="lds-roller"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
    </div>
  </div>
</template>

<script>
import CourseItem from "@/components/course/CourseItem";
import FilterContent from "@/components/course/FilterContent";
import axios from "axios";
export default {
  name: "Courses",
  components: {FilterContent, CourseItem},
  data() {
    return {
      courses: null,
      hide: false,
      slug: this.$route.params.slug,
      loading: false
    }
  },
  methods: {
    hideFilter() {
      this.hide = !this.hide
    }
  },
  watch: {
    $route (to, from) {
      this.loading = true
      axios.get('courses/search?q=' + this.$route.params.slug)
          .then(res => {
            this.courses = res.data
            console.log(res.data)
            this.loading = false
          })
    }
  },
  async created() {
    await axios.get('courses/search?q=' + this.slug)
    .then(res => {
      this.courses = res.data
      console.log(res.data)
    })
  }
}
</script>

<style lang="scss" scoped>
  //.hide {
  //  transform: translateX(-400px) !important;
  //  transition: all 0.5s ease;
  //  position: absolute;
  //}
  //.filter {
  //  transform: translateX(0);
  //  transition: all 0.5s ease;
  //}
  //
  //.activeHide {
  //  width: 100%;
  //  transform: translateX(0);
  //  transition: all 0.5s ease;
  //}
</style>